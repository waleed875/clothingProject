package com.clothing.store.mapper;

import com.clothing.store.dto.address.AddressResponse;
import com.clothing.store.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toResponse(Address address);
}
