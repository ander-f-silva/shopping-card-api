package io.shoppingcard.infrastructure.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.shoppingcard.infrastructure.database.entity.ProductEntity;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Factory
class StorageMemory {
    private static final Logger logger = LoggerFactory.getLogger(StorageMemory.class);

    @Value("${sourcePath}")
    private String sourcePath;

    @Singleton
    List<ProductEntity> products() throws IOException {
        try (InputStream is = TypeReference.class.getResourceAsStream(sourcePath)) {
            String jsonText = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            return Arrays.asList(new ObjectMapper().readValue(jsonText, ProductEntity[].class));
        } catch (JsonProcessingException jsonProcessingException) {
            logger.error("[event: create object] Message: {}", jsonProcessingException.getMessage(), jsonProcessingException);
        }

        logger.error("[event: load file] Message: Failed to load file products.json");

        throw new IOException("Failed to load file products.json");
    }
}
