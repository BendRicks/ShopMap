package ru.bendricks.shopmap.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final String entityName;

    public NotFoundException(String entityName) {
        this.entityName = entityName;
    }

}
