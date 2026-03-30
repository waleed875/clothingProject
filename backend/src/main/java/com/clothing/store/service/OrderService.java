package com.clothing.store.service;

import com.clothing.store.dto.order.CreateOrderRequest;
import com.clothing.store.dto.order.OrderItemResponse;
import com.clothing.store.dto.order.OrderResponse;
import com.clothing.store.entity.OrderEntity;
import com.clothing.store.entity.OrderItem;
import com.clothing.store.entity.StatusEnums;
import com.clothing.store.exception.ConflictException;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CurrentUserService currentUserService;
    private final AddressRepository addressRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        var user = currentUserService.getCurrentUser();
        var address = addressRepository.findByIdAndUserId(request.addressId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        var cart = cartService.getOrCreateCart();
        var cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new ConflictException("Cart is empty");
        }

        for (var item : cartItems) {
            var inventory = inventoryRepository.findByVariantId(item.getVariant().getId())
                    .orElseThrow(() -> new ConflictException("Inventory not found for variant"));
            int available = inventory.getQuantity() - inventory.getReservedQuantity();
            if (available < item.getQuantity()) {
                throw new ConflictException("Insufficient stock for SKU: " + item.getVariant().getSku());
            }
        }

        var order = new OrderEntity();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus(StatusEnums.OrderStatus.PENDING);
        order.setShippingCost(BigDecimal.ZERO);
        order.setOrderNumber(generateOrderNumber());
        order.setSubtotal(BigDecimal.ZERO);
        order.setTotalAmount(BigDecimal.ZERO);
        order = orderRepository.save(order);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (var cartItem : cartItems) {
            var variant = cartItem.getVariant();
            var totalPrice = variant.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            subtotal = subtotal.add(totalPrice);

            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVariant(variant);
            orderItem.setProductName(variant.getProduct().getName());
            orderItem.setSku(variant.getSku());
            orderItem.setSize(variant.getSize());
            orderItem.setColor(variant.getColor());
            orderItem.setUnitPrice(variant.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(totalPrice);
            orderItemRepository.save(orderItem);

            var inventory = inventoryRepository.findByVariantId(variant.getId())
                    .orElseThrow(() -> new ConflictException("Inventory not found for variant"));
            inventory.setQuantity(inventory.getQuantity() - cartItem.getQuantity());
            inventoryRepository.save(inventory);
        }

        order.setSubtotal(subtotal);
        order.setTotalAmount(subtotal.add(order.getShippingCost()));
        orderRepository.save(order);

        cartItemRepository.deleteByCartId(cart.getId());
        return toResponse(order);
    }

    public List<OrderResponse> getMyOrders() {
        var user = currentUserService.getCurrentUser();
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream().map(this::toResponse).toList();
    }

    public OrderResponse getMyOrder(Long orderId) {
        var user = currentUserService.getCurrentUser();
        var order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toResponse(order);
    }

    private OrderResponse toResponse(OrderEntity order) {
        var items = orderItemRepository.findByOrderId(order.getId()).stream().map(item -> new OrderItemResponse(
                item.getId(),
                item.getVariant().getId(),
                item.getProductName(),
                item.getSku(),
                item.getSize(),
                item.getColor(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getTotalPrice()
        )).toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getShippingAddress() != null ? order.getShippingAddress().getId() : null,
                order.getSubtotal(),
                order.getShippingCost(),
                order.getTotalAmount(),
                items
        );
    }

    private String generateOrderNumber() {
        String orderNumber;
        do {
            orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (orderRepository.existsByOrderNumber(orderNumber));
        return orderNumber;
    }
}
