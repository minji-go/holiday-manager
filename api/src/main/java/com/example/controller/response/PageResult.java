package com.example.controller.response;

import java.util.List;

public record PageResult<T>(int page, int size, long totalElements, List<T> contents) {
}
