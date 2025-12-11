package com.pragma.powerup.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String input);
    boolean matches(String rawPassword, String encodePassword);
}
