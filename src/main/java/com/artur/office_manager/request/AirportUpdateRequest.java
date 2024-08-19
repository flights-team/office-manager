package com.artur.office_manager.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record AirportUpdateRequest (
        @NotNull
        String id,
        @Nullable
        String name,
        @Nullable
        Float positionX,
        @Nullable
        Float positionY
) implements UpdateRequest{}
