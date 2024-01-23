package com.fiap.postech.techchallenge.fastfoodpayment.application.api.config;

import lombok.Getter;

@Getter
public class ApiError {

    private String message;
    private int status;

    public ApiError(String message, int statusCode){
        this.message = message;
        this.status = statusCode;
    }
}
