package com.artur.office_manager.request;


public record AirportCreateRequest(
        String name,
        float positionX,
        float positionY
) implements CreateRequest{ }
