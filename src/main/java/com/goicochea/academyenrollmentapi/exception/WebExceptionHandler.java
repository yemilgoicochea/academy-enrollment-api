package com.goicochea.academyenrollmentapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Order(-1)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse); //req -> renderErrorResponse(req)
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest req) {
        Map<String, Object> generalError = getErrorAttributes(req, ErrorAttributeOptions.defaults());
        Map<String, Object> customError = new HashMap<>();

        int statusCode = Integer.parseInt(String.valueOf(generalError.get("status")));
        Throwable error = getError(req);
        log.error("[ERROR] {}", error.getMessage(), error);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        switch (statusCode){
            case 400, 422 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 400);
                httpStatus = HttpStatus.BAD_REQUEST;
            }
            case 404 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 404);
                httpStatus = HttpStatus.NOT_FOUND;
            }
            case 401, 403 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 401);
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            case 500 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 500);
            }
            default -> {
                customError.put("message", error.getMessage());
                customError.put("status", 418);
                httpStatus = HttpStatus.I_AM_A_TEAPOT;
            }
        }

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customError));
    }
}
