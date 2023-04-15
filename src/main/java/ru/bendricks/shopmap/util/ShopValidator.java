package ru.bendricks.shopmap.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bendricks.shopmap.dto.entity.AddressDTO;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.dto.entity.create.AddressCreateDTO;
import ru.bendricks.shopmap.dto.entity.create.ShopCreateDTO;

@Component
public class ShopValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ShopDTO.class) || clazz.equals(ShopCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ShopCreateDTO shop = (ShopCreateDTO) target;
        if (shop.getCreator() == null) {
            errors.rejectValue("creator", "", "User id not found");
        }
    }

    public void validateForChange(Object target, Errors errors) {
        ShopDTO shopDTO = (ShopDTO) target;
        if (shopDTO.getId() == null){
            errors.rejectValue("id", "", "Address id not found");
        }
        if (shopDTO.getCreator() != null
                && shopDTO.getCreator().getId() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }

}
