package io.shoppingcard.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.shoppingcard.application.payload.ProductsRequest;
import io.shoppingcard.application.payload.ProductsResponse;
import io.shoppingcard.business.dto.BlackFridayEvent;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@MicronautTest
class CheckoutResourceTest {
    private static final String PATH_CHECKOUT = "/api/v1/checkout";

    @Inject
    @Client("/")
    private RxHttpClient client;

    @Inject
    private BlackFridayEvent blackFridayEvent;

    @Test
    @DisplayName("should assemble the shopping card")
    public void testDoCheckoutReturnOk() throws Exception {
        var requestJson = "{\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"quantity\": 4 \n" +
                "        },\n" +
                "         {\n" +
                "            \"id\": 2,\n" +
                "            \"quantity\": 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        var objectMapper = new ObjectMapper();

        var body = objectMapper.readValue(requestJson, ProductsRequest.class);

        when(blackFridayEvent.isNow()).thenReturn(false);

        final ProductsResponse response = client.toBlocking().retrieve(HttpRequest.POST(PATH_CHECKOUT, body), ProductsResponse.class);

        verify(blackFridayEvent, times(1)).isNow();

        var responseJson = "{\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"quantity\": 4,\n" +
                "            \"discount\": 0,\n" +
                "            \"unit_amount\": 60356,\n" +
                "            \"total_amount\": 241424,\n" +
                "            \"is_gift\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"quantity\": 2,\n" +
                "            \"discount\": 0,\n" +
                "            \"unit_amount\": 93811,\n" +
                "            \"total_amount\": 187622,\n" +
                "            \"is_gift\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_amount\": 429046,\n" +
                "    \"total_amount_with_discount\": 429046,\n" +
                "    \"total_discount\": 0\n" +
                "}";

        var expectedResponse = objectMapper.readValue(responseJson, ProductsResponse.class);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("should fail when the request body is null")
    public void testDoCheckoutReturnBadRequestToProductNull() {
        try {
            client.toBlocking().retrieve(HttpRequest.POST(PATH_CHECKOUT, null), ProductsResponse.class);
        } catch (HttpClientResponseException httpClientResponseException) {
           assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.getStatus());
           assertEquals("Bad Request", httpClientResponseException.getMessage());
        }
    }

    @Test
    @DisplayName("should fail when the product.id is null")
    public void testDoCheckoutReturnBadRequestToProductIdNull() throws Exception {
        var requestJson = "{\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"id\": null,\n" +
                "            \"quantity\": 4 \n" +
                "        },\n" +
                "         {\n" +
                "            \"id\": 2,\n" +
                "            \"quantity\": 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        var objectMapper = new ObjectMapper();

        var body = objectMapper.readValue(requestJson, ProductsRequest.class);

        try {
            client.toBlocking().retrieve(HttpRequest.POST(PATH_CHECKOUT, body), ProductsResponse.class);
        } catch (HttpClientResponseException httpClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.getStatus());
            assertEquals("Bad Request", httpClientResponseException.getMessage());
        }
    }

    @Test
    @DisplayName("should fail when the product.quantity is null")
    public void testDoCheckoutReturnBadRequestToQuantityNull() throws Exception {
        var requestJson = "{\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"quantity\": null \n" +
                "        },\n" +
                "         {\n" +
                "            \"id\": 2,\n" +
                "            \"quantity\": 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        var objectMapper = new ObjectMapper();

        var body = objectMapper.readValue(requestJson, ProductsRequest.class);

        try {
            client.toBlocking().retrieve(HttpRequest.POST(PATH_CHECKOUT, body), ProductsResponse.class);
        } catch (HttpClientResponseException httpClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.getStatus());
            assertEquals("Bad Request", httpClientResponseException.getMessage());
        }
    }


    @Test
    @DisplayName("should fail when the product not found")
    public void testValidateProductExist() throws Exception {
        var requestJson = "{\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"id\": 10,\n" +
                "            \"quantity\": 1 \n" +
                "        },\n" +
                "         {\n" +
                "            \"id\": 2,\n" +
                "            \"quantity\": 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        var objectMapper = new ObjectMapper();

        var body = objectMapper.readValue(requestJson, ProductsRequest.class);

        try {
            client.toBlocking().retrieve(HttpRequest.POST(PATH_CHECKOUT, body), ProductsResponse.class);
        } catch (HttpClientResponseException httpClientResponseException) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpClientResponseException.getStatus());
            assertEquals("Unprocessable Entity", httpClientResponseException.getMessage());
        }
    }

    @MockBean(BlackFridayEvent.class)
    BlackFridayEvent isBlackFriday() {
        return mock(BlackFridayEvent.class);
    }
}