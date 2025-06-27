package com.example.controller.response;


import com.example.ErrorType;

public record ErrorResponse(String errorMessage, ErrorType errorType) {
}
