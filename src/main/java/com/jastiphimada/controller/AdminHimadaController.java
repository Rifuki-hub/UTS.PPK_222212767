package com.jastiphimada.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.jastiphimada.dto.HimadaDto;
import com.jastiphimada.service.HimadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Manajemen Himada Admin", description = "Operasi terkait manajemen Himada oleh admin")
@RestController
@RequestMapping("/admin/himada")  // Prefix URL khusus untuk manajemen Himada
public class AdminHimadaController {

    @Autowired
    private HimadaService himadaService;

    @Operation(summary = "Menambah Himada Jastip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Himada berhasil dibuat",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HimadaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<HimadaDto> createHimada(@RequestBody HimadaDto himadaDto) {
        HimadaDto himada = himadaService.createHimada(himadaDto);
        return ResponseEntity.ok(himada);
    }

    @Operation(summary = "Melihat semua data Himada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Daftar semua Himada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HimadaDto.class, type = "array")) })
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<HimadaDto>> getAllHimada() {
        List<HimadaDto> himadas = himadaService.getAllHimada();
        return ResponseEntity.ok(himadas);
    }

    @Operation(summary = "Mengedit Himada Jastip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Himada berhasil diperbarui",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HimadaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Himada tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HimadaDto> updateHimada(@PathVariable Long id, @RequestBody HimadaDto himadaDto) {
        HimadaDto updatedHimada = himadaService.updateHimada(id, himadaDto);
        return ResponseEntity.ok(updatedHimada);
    }

    @Operation(summary = "Menghapus Himada Jastip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Himada berhasil dihapus",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Himada tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHimada(@PathVariable Long id) {
        himadaService.deleteHimada(id);
        return ResponseEntity.ok("Himada berhasil dihapus.");
    }
}