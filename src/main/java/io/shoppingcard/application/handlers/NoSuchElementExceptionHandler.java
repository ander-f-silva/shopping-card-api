package io.shoppingcard.application.handlers;


import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.util.HashMap;


import java.util.NoSuchElementException;

@Produces
@Singleton
@Requires(classes = {NoSuchElementException.class, ExceptionHandler.class})
public class NoSuchElementExceptionHandler implements ExceptionHandler<NoSuchElementException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, NoSuchElementException exception) {
        var message = new HashMap<String, String>();
        message.put("error", exception.getMessage());
        return HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
    }
}
