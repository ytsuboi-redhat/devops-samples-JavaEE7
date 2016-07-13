package com.redhat.sample.cicd.rest.schema;

import java.io.Serializable;

public class OrderResponse<T> implements Serializable {

    public static final String RESPONSE_CODE_SUCCESS = "success";

    public static final String RESPONSE_CODE_FAILURE = "failure";

    // ---------------------------------------------------- Instance Variables

    private String code;

    private String description;

    private T value;

    // ---------------------------------------------------------- Constructors

    public OrderResponse() {
    }

    public OrderResponse(String code) {
        this.code = code;
    }

    public OrderResponse(String code, String description, T value) {
        this.code = code;
        this.description = description;
        this.value = value;
    }

    // ------------------------------------------------------------- Accessors

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
