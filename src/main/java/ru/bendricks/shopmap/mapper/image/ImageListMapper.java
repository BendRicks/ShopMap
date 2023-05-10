package ru.bendricks.shopmap.mapper.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.AdditionalImageDTO;
import ru.bendricks.shopmap.dto.entity.AddressDTO;
import ru.bendricks.shopmap.entity.AdditionalImage;
import ru.bendricks.shopmap.entity.Address;
import ru.bendricks.shopmap.mapper.address.AddressMapper;

import java.util.List;

@Component
public class ImageListMapper {

    private final ImageMapper imageMapper;

    @Autowired
    public ImageListMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    public List<AdditionalImage> dtoToModel(List<AdditionalImageDTO> additionalImageDTOs){
        if (additionalImageDTOs == null){
            return null;
        }
        return additionalImageDTOs.stream().map(imageMapper::toModel).toList();
    };
    public List<AdditionalImage> createDTOToModel(List<AdditionalImageDTO> additionalImageDTOs){
        if (additionalImageDTOs == null){
            return null;
        }
        return additionalImageDTOs.stream().map(imageMapper::createDTOtoModel).toList();
    };
    public List<AdditionalImageDTO> toDTO(List<AdditionalImage> additionalImages){
        if (additionalImages == null){
            return null;
        }
        return additionalImages.stream().map(imageMapper::toDTO).toList();
    };

}
