package com.deye.xl.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceResponse<T> extends ResponseEntity<T> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public ServiceResponse(T data) {
        super(data, HttpStatus.OK);
    }

    public ServiceResponse(T data, HttpStatus status) {
        super(data, status);
    }

    public static ServiceResponse<JsonNode> getDefaultServiceOkResponse() {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("message", "Success");
        return new ServiceResponse<>(objectNode, HttpStatus.OK);
    }

    public static ServiceResponse<JsonNode> getDefaultServiceFailedResponse(String message) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("message", message);
        return new ServiceResponse<>(objectNode, HttpStatus.BAD_REQUEST);
    }

    /**
     * OK response with some message
     */
    public static ServiceResponse<JsonNode> getDefaultServiceOkResponse(String message) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("message", message);
        return new ServiceResponse<>(objectNode, HttpStatus.OK);
    }

    /**
     * OK response with some message object
     */
    public static ServiceResponse<JsonNode> getDefaultServiceOkResponse(Object message) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("message", message.toString());
        return new ServiceResponse<>(objectNode, HttpStatus.OK);
    }

}
