package ru.bendricks.shopmap.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bendricks.shopmap.dto.entity.AddressDTO;
import ru.bendricks.shopmap.dto.entity.create.AddressCreateDTO;

@Component
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AddressDTO.class) || clazz.equals(AddressCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddressCreateDTO address = (AddressCreateDTO) target;
        if (address.getShop() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }

    public void validateForChange(Object target, Errors errors) {
        AddressDTO addressDTO = (AddressDTO) target;
        if (addressDTO.getId() == null){
            errors.rejectValue("id", "", "Address id not found");
        }
        if (addressDTO.getShop() != null
                && addressDTO.getShop().getId() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }
}
