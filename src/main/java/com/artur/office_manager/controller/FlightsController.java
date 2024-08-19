package com.artur.office_manager.controller;

import com.artur.common.bean.board.Board;
import com.artur.office_manager.request.FlightCreateRequest;
import com.artur.office_manager.service.FlightsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;


    @Operation(description = "Creates a flight for the specified date. If date is now, the flight will start.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight was created successfully",
                    content = @Content(schema = @Schema(implementation = Board.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "If specified data is wrong",
                    content = @Content()
            )
    })
    @PostMapping("")
    public ResponseEntity<?> createFlight(@ModelAttribute FlightCreateRequest flightCreateRequest) {
        try {
            return ResponseEntity.ok(flightsService.createFlight());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
