package ru.bendricks.shopmap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {

    private String message;
    private Long timestamp;

}
