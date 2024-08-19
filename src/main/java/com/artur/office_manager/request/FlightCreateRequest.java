package com.artur.office_manager.request;


import java.time.Instant;

public record FlightCreateRequest (
        String departureAirport,
        String destinationAirport,
        Instant instant
) implements CreateRequest{ }
