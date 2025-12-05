package com.pragma.powerup.domain.util;

public final class ValidationConstants {

    private ValidationConstants() {}

    public static final String EMAIL_REGEX   = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX   = "^\\+?\\d{10,13}$";
    public static final String DOCUMENT_REGEX = "^\\d+$";
    public static final int MIN_AGE = 18;
}
