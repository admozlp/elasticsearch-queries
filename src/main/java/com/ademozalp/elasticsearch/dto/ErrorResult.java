package com.ademozalp.elasticsearch.dto;

public record ErrorResult(Boolean success, String message, Integer code) { }
