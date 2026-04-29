package br.com.pradolabs.demo.rest;

public record StudentErrorResponse(
        int status,
        String message,
        long timeStamp
) {
    public StudentErrorResponse {
    }
}
