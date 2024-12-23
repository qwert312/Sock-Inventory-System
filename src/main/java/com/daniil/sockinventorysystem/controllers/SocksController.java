package com.daniil.sockinventorysystem.controllers;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.dto.SocksDTO;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.dto.SocksSearchCriteriaDTO;
import com.daniil.sockinventorysystem.exceptions.MappingException;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import com.daniil.sockinventorysystem.mappers.DTOMapper;
import com.daniil.sockinventorysystem.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SocksController {

    private SocksService socksService;
    private DTOMapper dtoMapper;

    public SocksController(SocksService socksService, DTOMapper dtoMapper) {
        this.socksService = socksService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/api/socks")
    @Operation(summary = "Returns quantity of socks based on provided filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<Integer> getSocksQuantityWithFilters(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String comparisonOperator,
            @RequestParam(required = false) Integer cottonPercentage)
    {
        SocksSearchCriteriaDTO socksSearchCriteriaDto = new SocksSearchCriteriaDTO(color, comparisonOperator, cottonPercentage);
        SocksSearchCriteria socksSearchCriteria;
        try {
            socksSearchCriteria = dtoMapper.dtoToSocksSearchCriteria(socksSearchCriteriaDto);
        } catch (MappingException ex) {
            try {
                throw new MethodArgumentTypeMismatchException(
                        comparisonOperator,
                        ComparisonOperator.class,
                        "comparisonOperator",
                        new MethodParameter(
                                this.getClass().getDeclaredMethod(
                                        "getSocksQuantityWithFilters",
                                        String.class,
                                        String.class,
                                        Integer.class),
                                1),
                        ex);
            } catch (NoSuchMethodException runtEx) {
                throw new RuntimeException();//it should not have happened
            }
        }
        int socksQuantity  = socksService.getSocksQuantity(socksSearchCriteria);
        return ResponseEntity.ok(socksQuantity);
    }

    @PostMapping("/api/socks/income")
    @Operation(summary = "Adds socks or increases quantity of existing ones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<Void> addOrIncreaseSocks(@RequestBody SocksDTO socksDTO) {
        Socks socks = dtoMapper.dtoToSocks(socksDTO);
        socksService.addOrIncreaseSocksQuantity(socks);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/socks/outcome")
    @Operation(summary = "Decreases quantity of existing socks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<Void> decreaseSocks(@RequestBody SocksDTO socksDTO) {
        Socks socks = dtoMapper.dtoToSocks(socksDTO);
        socksService.decreaseSocksQuantity(socks);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/socks/batch")
    @Operation(summary = "Adds or increases the quantity of socks from a CSV file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<Void> addOrIncreaseSocksFromCSV(@RequestParam MultipartFile file) {
        socksService.addOrIncreaseSocksQuantityFromCSV(file);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/socks/{id}")
    @Operation(summary = "Updates existing socks by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<Void> updateSocks(@PathVariable Long id, @RequestBody SocksDTO socksDTO) {
        Socks socks = dtoMapper.dtoToSocks(socksDTO);
        socksService.update(id, socks);
        return ResponseEntity.ok().build();
    }
}
