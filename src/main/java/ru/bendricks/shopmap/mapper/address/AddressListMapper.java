package ru.bendricks.shopmap.mapper.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.AddressDTO;
import ru.bendricks.shopmap.dto.entity.create.AddressCreateDTO;
import ru.bendricks.shopmap.entity.Address;
import java.util.List;

@Component
public class AddressListMapper {

    private final AddressMapper addressMapper;

    @Autowired
    public AddressListMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public List<Address> dtoToModel(List<AddressDTO> addressDTOList){
        if (addressDTOList == null){
            return null;
        }
        return addressDTOList.stream().map(addressMapper::toModel).toList();
    };
    public List<Address> createDTOToModel(List<AddressCreateDTO> addressList){
        if (addressList == null){
            return null;
        }
        return addressList.stream().map(addressMapper::toModel).toList();
    };
    public List<AddressDTO> toDTO(List<Address> addressList){
        if (addressList == null){
            return null;
        }
        return addressList.stream().map(addressMapper::toDTORestricted).toList();
    };

}
