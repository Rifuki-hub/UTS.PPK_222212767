package com.jastiphimada.controller;

import com.jastiphimada.dto.StatusRequest;
import com.jastiphimada.dto.TransaksiDto;
import com.jastiphimada.service.TransaksiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Manajemen Transaksi Admin", description = "Operasi terkait manajemen transaksi oleh admin")
@RestController
@RequestMapping("/admin/transaksi")  // Prefix URL khusus untuk manajemen transaksi
public class AdminTransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @Operation(summary = "Melihat semua transaksi jastip",
               responses = {
                   @ApiResponse(responseCode = "200", 
                                description = "Daftar semua transaksi",
                                content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(implementation = TransaksiDto.class, type = "array"))),
                   @ApiResponse(responseCode = "403", description = "Akses ditolak, hanya admin yang dapat mengakses",
                           content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string")))
               })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<TransaksiDto>> getAllTransaksi() {
        List<TransaksiDto> transaksis = transaksiService.getAllTransaksi();
        return ResponseEntity.ok(transaksis);
    }

    @Operation(summary = "Mengedit status transaksi jastip",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   description = "Status transaksi yang akan diperbarui",
                   content = @Content(mediaType = "application/json", 
                                      schema = @Schema(implementation = StatusRequest.class))),
               responses = {
                   @ApiResponse(responseCode = "200", 
                                description = "Transaksi berhasil diperbarui",
                                content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(implementation = TransaksiDto.class))),
                   @ApiResponse(responseCode = "404", description = "Transaksi tidak ditemukan",content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "403", description = "Akses ditolak, hanya admin yang dapat mengakses",content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string")))
               })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TransaksiDto> updateStatusTransaksi(@PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        TransaksiDto updatedTransaksi = transaksiService.updateStatusTransaksi(id, statusRequest.getStatus());
        return ResponseEntity.ok(updatedTransaksi);
    }

    @Operation(summary = "Menghapus transaksi",
               responses = {
                   @ApiResponse(responseCode = "200", 
                                description = "Transaksi berhasil dihapus",
                                content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "404", description = "Transaksi tidak ditemukan"),
                   @ApiResponse(responseCode = "403", description = "Akses ditolak, hanya admin yang dapat mengakses")
               })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaksi(@PathVariable Long id) {
        transaksiService.deleteTransaksi(id);
        return ResponseEntity.ok("Transaksi berhasil dihapus.");
    }
}