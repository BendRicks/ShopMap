package ru.bendricks.shopmap.exception;

public class NotEnoughAuthoritiesException extends RuntimeException{

    public NotEnoughAuthoritiesException(String msg){
        super(msg);
    }

}
