package com.jastiphimada.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.jastiphimada.dto.BarangDto;
import com.jastiphimada.service.BarangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Manajemen Barang Admin", description = "Operasi terkait manajemen barang oleh admin")
@RestController
@RequestMapping("/admin/barang")  // Prefix URL khusus untuk manajemen barang
public class AdminBarangController {

    @Autowired
    private BarangService barangService;

    @Operation(summary = "Melihat semua produk jastip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Daftar semua produk jastip",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarangDto.class, type = "array")) })
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<BarangDto>> getAllBarang() {
        List<BarangDto> barangs = barangService.getAllBarang();
        return ResponseEntity.ok(barangs);
    }

    @Operation(summary = "Melihat produk jastip berdasarkan Himada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Daftar produk jastip berdasarkan Himada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarangDto.class, type = "array")) })
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/himada/{himadaId}")
    public ResponseEntity<List<BarangDto>> getBarangByHimada(@PathVariable Long himadaId) {
        List<BarangDto> barangs = barangService.getBarangByHimada(himadaId);
        return ResponseEntity.ok(barangs);
    }

    @Operation(summary = "Menambah produk jastip berdasarkan Himada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produk jastip berhasil ditambahkan",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarangDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/himada/{himadaId}")
    public ResponseEntity<BarangDto> createBarang(@RequestBody BarangDto barangDto) {
        BarangDto newBarang = barangService.createBarang(barangDto);
        return ResponseEntity.ok(newBarang);
    }

    @Operation(summary = "Mengedit produk jastip berdasarkan Himada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produk jastip berhasil diperbarui",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarangDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produk tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BarangDto> updateBarang(@PathVariable Long id, @RequestBody BarangDto barangDto) {
        BarangDto updatedBarang = barangService.updateBarang(id, barangDto);
        return ResponseEntity.ok(updatedBarang);
    }

    @Operation(summary = "Menghapus produk jastip berdasarkan Himada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produk jastip berhasil dihapus",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produk tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBarang(@PathVariable Long id) {
        barangService.deleteBarang(id);
        return ResponseEntity.ok("Barang berhasil dihapus.");
    }
}