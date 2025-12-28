package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IAuthService;
import com.pragma.powerup.domain.api.IEmployeeRestaurantService;
import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.AuthUseCase;
import com.pragma.powerup.domain.usecase.EmployeeRestaurantUseCase;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.domain.validation.UserBusinessValidator;
import com.pragma.powerup.domain.validation.UserDataValidator;
import com.pragma.powerup.infrastructure.out.jpa.adapter.JwtProviderAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public IJwtProviderPort jwtProviderPort(SecretKey jwtSecretKey) {
        return new JwtProviderAdapter(jwtSecretKey, jwtExpiration);
    }

    @Bean
    public IUserPersistencePort userPersistencePort(IUserRepository repository,
                                                    IUserEntityMapper mapper) {
        return new UserJpaAdapter(repository, mapper);
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }

    @Bean
    public UserDataValidator userDataValidator() {
        return new UserDataValidator();
    }

    @Bean
    public UserBusinessValidator userBusinessValidator(IUserPersistencePort persistencePort) {
        return new UserBusinessValidator(persistencePort);
    }

    @Bean
    public IUserService userServicePort(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort,
            IRestaurantExternalServicePort restaurantExternalServicePort,
            UserDataValidator dataValidator,
            UserBusinessValidator businessValidator
    ) {
        return new UserUseCase(
                userPersistencePort,
                passwordEncoderPort,
                restaurantExternalServicePort,
                dataValidator,
                businessValidator
        );
    }
    @Bean
    public IAuthService authService(IUserPersistencePort userPersistencePort,
                                    IPasswordEncoderPort passwordEncoderPort,
                                    IJwtProviderPort jwtProviderPort) {
        return new AuthUseCase(userPersistencePort, passwordEncoderPort, jwtProviderPort);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IEmployeeRestaurantService employeeRestaurantService(IEmployeeRestaurantQueryPort port) {
        return new EmployeeRestaurantUseCase(port);
    }
}
