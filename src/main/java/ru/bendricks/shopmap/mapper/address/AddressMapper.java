package ru.bendricks.shopmap.mapper.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.AddressDTO;
import ru.bendricks.shopmap.dto.entity.create.AddressCreateDTO;
import ru.bendricks.shopmap.entity.Address;
import ru.bendricks.shopmap.mapper.shop.ShopMapper;

@Component
public class AddressMapper {

//    private final ShopMapper shopMapper;
//
//    @Autowired
//    public AddressMapper(@Lazy ShopMapper shopMapper) {
//        this.shopMapper = shopMapper;
//    }

    public Address toModel(AddressDTO addressDTO) {
        if (addressDTO == null){
            return null;
        }
        return Address.builder()
                .id(addressDTO.getId())
                .city(addressDTO.getCity())
                .district(addressDTO.getDistrict())
                .street(addressDTO.getStreet())
                .building(addressDTO.getBuilding())
                .room(addressDTO.getRoom())
                .build();
    }

    public Address toModel(AddressCreateDTO addressCreateDTO) {
        if (addressCreateDTO == null){
            return null;
        }
        return Address.builder()
                .city(addressCreateDTO.getCity())
                .district(addressCreateDTO.getDistrict())
                .street(addressCreateDTO.getStreet())
                .building(addressCreateDTO.getBuilding())
                .room(addressCreateDTO.getRoom())
                .build();
    }

    ;

    public AddressDTO toDTO(Address address) {
        if (address == null){
            return null;
        }
        return AddressDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .district(address.getDistrict())
                .street(address.getStreet())
                .building(address.getBuilding())
                .room(address.getRoom())
                .build();
    }

    public AddressDTO toDTORestricted(Address address) {
        if (address == null){
            return null;
        }
        return AddressDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .district(address.getDistrict())
                .street(address.getStreet())
                .building(address.getBuilding())
                .room(address.getRoom())
                .build();
    }

}
