package ru.bendricks.shopmap.mapper.image;

import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.AdditionalImageDTO;
import ru.bendricks.shopmap.dto.entity.AddressDTO;
import ru.bendricks.shopmap.entity.AdditionalImage;
import ru.bendricks.shopmap.entity.Address;

@Component
public class ImageMapper {

    public AdditionalImage toModel(AdditionalImageDTO imageDTO) {
        if (imageDTO == null){
            return null;
        }
        return AdditionalImage.builder()
                .id(imageDTO.getId())
                .imageURL(imageDTO.getImageUrl())
                .imageDescription(imageDTO.getImageDescription())
                .build();
    }

    public AdditionalImage createDTOtoModel(AdditionalImageDTO imageDTO) {
        if (imageDTO == null){
            return null;
        }
        return AdditionalImage.builder()
                .imageURL(imageDTO.getImageUrl())
                .imageDescription(imageDTO.getImageDescription())
                .build();
    }

    public AdditionalImageDTO toDTO(AdditionalImage image) {
        if (image == null){
            return null;
        }
        return AdditionalImageDTO.builder()
                .id(image.getId())
                .imageUrl(image.getImageURL())
                .imageDescription(image.getImageDescription())
                .build();
    }

}
