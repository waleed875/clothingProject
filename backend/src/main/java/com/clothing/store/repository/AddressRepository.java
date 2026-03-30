package com.clothing.store.repository;

import com.clothing.store.entity.Address;
import com.clothing.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserIdOrderByIdDesc(Long userId);
    Optional<Address> findByIdAndUserId(Long id, Long userId);
    Optional<Address> findByUserAndIsDefaultTrue(User user);
}
