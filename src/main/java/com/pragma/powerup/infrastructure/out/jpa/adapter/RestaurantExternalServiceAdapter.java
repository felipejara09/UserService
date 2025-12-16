package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.spi.IRestaurantExternalServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestaurantExternalServiceAdapter implements IRestaurantExternalServicePort {

    private final RestTemplate restTemplate;

    @Value("${services.plazoleta.base-url}")
    private String plazoletaBaseUrl;

    @Override
    public boolean isRestaurantOwnedBy(Long restaurantId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = plazoletaBaseUrl + "/api/v1/restaurants/" + restaurantId + "/ownership";

        try {
            ResponseEntity<Boolean> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);

            return Boolean.TRUE.equals(response.getBody());

        } catch (HttpClientErrorException e) {
            System.out.println("Ownership check failed. URL=" + url
                    + " status=" + e.getStatusCode()
                    + " body=" + e.getResponseBodyAsString());
            return false;
        }
    }
}




