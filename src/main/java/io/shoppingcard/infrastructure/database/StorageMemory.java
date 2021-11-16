package io.shoppingcard.infrastructure.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Factory;
import io.shoppingcard.infrastructure.database.entity.ProductEntity;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Factory
public class StorageMemory {

    @Singleton
    List<ProductEntity> products() throws IOException {
        //TODO: The path is in env path
        try (InputStream is = TypeReference.class.getResourceAsStream("/json/products.json")) {
            String jsonText = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            return Arrays.asList(new ObjectMapper().readValue(jsonText, ProductEntity[].class));
        } catch (JsonProcessingException e) {
            //TODO: Add an logger with error
            e.printStackTrace();
        }

        //TODO: Adjusted message
        throw new IOException("Error when load the file of products.json");
    }
}
