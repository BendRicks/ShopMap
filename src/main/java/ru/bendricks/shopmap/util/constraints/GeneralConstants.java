package ru.bendricks.shopmap.util.constraints;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneralConstants {

    public static final String BEARER_HEADER_TOKEN = "Bearer ";
    public static final int JWT_START_INDEX = 7;

    @UtilityClass
    public class SQLStateConstants {

        public static final String UNIQUE_CONSTRAINT = "23505";

    }

}
