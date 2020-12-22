package dev.mantasboro.trakt5.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Status implements TraktEnum {

    ENDED("ended"),
    RETURNING("returning series"),
    CANCELED("canceled"),
    IN_PRODUCTION("in production");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    private static final Map<String, Status> STRING_MAPPING = new HashMap<>();

    static {
        for (Status via : Status.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(Locale.ROOT), via);
        }
    }

    public static Status fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return value;
    }

}
