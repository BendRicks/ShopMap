package ru.bendricks.shopmap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.shopmap.dto.SearchResponse;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.dto.entity.create.ShopCreateDTO;
import ru.bendricks.shopmap.exception.NotCreatedException;
import ru.bendricks.shopmap.mapper.shop.ShopListMapper;
import ru.bendricks.shopmap.mapper.shop.ShopMapper;
import ru.bendricks.shopmap.service.ShopService;
import ru.bendricks.shopmap.util.ShopValidator;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;
    private final ShopMapper mapper;
    private final ShopListMapper listMapper;
    private final ShopValidator shopValidator;

    @Autowired
    public ShopController(ShopService shopService, ShopMapper mapper, ShopListMapper listMapper, ShopValidator shopValidator) {
        this.shopService = shopService;
        this.mapper = mapper;
        this.listMapper = listMapper;
        this.shopValidator = shopValidator;
    }

    @GetMapping("/")
    public SearchResponse getShops(@RequestParam(required = false, defaultValue = "") String nameLike,
                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "ASC") Sort.Direction dir,
                                   @RequestParam(required = false, defaultValue = "id") String param){
        return new SearchResponse(listMapper.toDTO(
                shopService.getShopsByNameAndPage(nameLike, page, size, dir, param)
        ), shopService.getShopsByNameAndPageMathcesAmount(nameLike));
    }

    @PostMapping("/")
    public ShopDTO createShop(@Valid @RequestBody ShopCreateDTO shopCreateDTO, BindingResult bindingResult){
        shopValidator.validate(shopCreateDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        return mapper.toDTO(
                shopService.createShop(
                        mapper.toModel(
                                shopCreateDTO
                        )
                )
        );
    }

    private void checkForAddChangeErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Shop not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
    }

}
