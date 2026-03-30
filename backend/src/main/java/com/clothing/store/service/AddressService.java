package com.clothing.store.service;

import com.clothing.store.dto.address.AddressRequest;
import com.clothing.store.dto.address.AddressResponse;
import com.clothing.store.entity.Address;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.mapper.AddressMapper;
import com.clothing.store.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CurrentUserService currentUserService;
    private final AddressMapper addressMapper;

    public List<AddressResponse> getMyAddresses() {
        var user = currentUserService.getCurrentUser();
        return addressRepository.findByUserIdOrderByIdDesc(user.getId()).stream().map(addressMapper::toResponse).toList();
    }

    @Transactional
    public AddressResponse create(AddressRequest request) {
        var user = currentUserService.getCurrentUser();
        if (request.isDefault()) {
            clearDefault(user);
        }

        var address = new Address();
        address.setUser(user);
        updateFields(address, request);
        return addressMapper.toResponse(addressRepository.save(address));
    }

    @Transactional
    public AddressResponse update(Long id, AddressRequest request) {
        var user = currentUserService.getCurrentUser();
        var address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (request.isDefault()) {
            clearDefault(user);
        }

        updateFields(address, request);
        return addressMapper.toResponse(addressRepository.save(address));
    }

    public void delete(Long id) {
        var user = currentUserService.getCurrentUser();
        var address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressRepository.delete(address);
    }

    @Transactional
    public AddressResponse setDefault(Long id) {
        var user = currentUserService.getCurrentUser();
        var address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        clearDefault(user);
        address.setIsDefault(true);
        return addressMapper.toResponse(addressRepository.save(address));
    }

    private void updateFields(Address address, AddressRequest request) {
        address.setLine1(request.line1());
        address.setLine2(request.line2());
        address.setCity(request.city());
        address.setState(request.state());
        address.setPostalCode(request.postalCode());
        address.setCountry(request.country());
        address.setIsDefault(request.isDefault());
    }

    private void clearDefault(com.clothing.store.entity.User user) {
        addressRepository.findByUserAndIsDefaultTrue(user).ifPresent(defaultAddress -> {
            defaultAddress.setIsDefault(false);
            addressRepository.save(defaultAddress);
        });
    }
}
