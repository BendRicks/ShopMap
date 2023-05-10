package ru.bendricks.shopmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.shopmap.dto.SearchResponse;
import ru.bendricks.shopmap.dto.entity.CreateInfo;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.dto.entity.UpdateInfo;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.exception.NotCreatedException;
import ru.bendricks.shopmap.service.ShopService;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/")
    public SearchResponse getShops(@RequestParam(required = false, defaultValue = "") String nameLike,
                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "ASC") Sort.Direction dir,
                                   @RequestParam(required = false, defaultValue = "id") String param) {
        return new SearchResponse(
                shopService.getShopsByNameAndPage(nameLike, page, size, dir, param),
                shopService.getShopsByNameAndStatusAmount(nameLike, ShopStatus.OPERATING));
    }

    @GetMapping("/moderation")
    public SearchResponse getShopsInModeration(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new SearchResponse(
                shopService.getShopsInModeration(page),
                shopService.getShopsInModerationAmount());
    }

    @PostMapping("/")
    public ShopDTO createShop(@Validated(CreateInfo.class) @RequestBody ShopDTO shopCreateDTO, BindingResult bindingResult) {
        checkForAddChangeErrors(bindingResult);
        return shopService.createShop(
                shopCreateDTO
        );
    }

    @PutMapping("/")
    public ShopDTO updateShop(@Validated(UpdateInfo.class) @RequestBody ShopDTO shopDTO, BindingResult bindingResult){
        checkForAddChangeErrors(bindingResult);
        return shopService.updateShop(
                shopDTO
        );
    }

    @GetMapping("/{id}")
    public ShopDTO getShopById(@PathVariable("id") Integer id) {
        return shopService.findShopById(id);
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