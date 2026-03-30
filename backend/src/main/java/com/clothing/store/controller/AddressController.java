package com.clothing.store.controller;

import com.clothing.store.dto.address.AddressRequest;
import com.clothing.store.dto.address.AddressResponse;
import com.clothing.store.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public List<AddressResponse> getMyAddresses() {
        return addressService.getMyAddresses();
    }

    @PostMapping
    public AddressResponse create(@Valid @RequestBody AddressRequest request) {
        return addressService.create(request);
    }

    @PutMapping("/{id}")
    public AddressResponse update(@PathVariable Long id, @Valid @RequestBody AddressRequest request) {
        return addressService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/default")
    public AddressResponse setDefault(@PathVariable Long id) {
        return addressService.setDefault(id);
    }
}
