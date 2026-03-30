package com.clothing.store.controller;

import com.clothing.store.dto.order.CreateOrderRequest;
import com.clothing.store.dto.order.OrderResponse;
import com.clothing.store.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/my")
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

    @GetMapping("/{orderId}")
    public OrderResponse getMyOrder(@PathVariable Long orderId) {
        return orderService.getMyOrder(orderId);
    }
}
