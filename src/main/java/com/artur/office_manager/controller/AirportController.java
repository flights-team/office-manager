package com.artur.office_manager.controller;

import com.artur.common.bean.office.AirPort;
import com.artur.office_manager.exception.NotFoundException;
import com.artur.office_manager.request.AirportCreateRequest;
import com.artur.office_manager.request.AirportUpdateRequest;
import com.artur.office_manager.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Operation(description = "Get an airport by specified id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport was found successfully",
                    content = @Content(schema = @Schema(implementation = AirPort.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "If airport with specified id was not found",
                    content = @Content()
            )
    })
    @GetMapping("")
    public ResponseEntity<?> findOfficeById(String id){
        try {
            return ResponseEntity.ok(airportService.getById(id));
        } catch (NotFoundException e){
            log.debug("Exception occurred while getting office by id", e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Get all offices available")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Offices was found successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AirPort.class)))
            )
    })
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllAirports(){
        return ResponseEntity.ok(airportService.getAll());
    }

    @Operation(description = "Create an airport")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport was created successfully",
                    content = @Content(schema = @Schema(implementation = AirPort.class))
            )
    })
    @PostMapping("")
    public ResponseEntity<?> createAirport(@ModelAttribute AirportCreateRequest airportCreateRequest){
        return ResponseEntity.ok(airportService.create(airportCreateRequest));
    }

    @Operation(description = "Update an airport")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport was updated successfully",
                    content = @Content(schema = @Schema(implementation = AirPort.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "If airport with specified id was not found",
                    content = @Content()
            )
    })
    @PutMapping("")
    public ResponseEntity<?> updateAirport(@ModelAttribute AirportUpdateRequest airportUpdateRequest){
        try {
            return ResponseEntity.ok(airportService.update(airportUpdateRequest));
        } catch (NotFoundException e) {
            log.debug("Exception occurred while updating office", e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Delete an airport")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport was updated successfully",
                    content = @Content()
            )
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAirport(@RequestParam String id){
        airportService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
