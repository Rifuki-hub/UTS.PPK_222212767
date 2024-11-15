package com.jastiphimada.controller;

import com.jastiphimada.dto.BarangDto;
import com.jastiphimada.dto.TransaksiDto;
import com.jastiphimada.dto.UserDto;
import com.jastiphimada.service.BarangService;
import com.jastiphimada.service.TransaksiService;
import com.jastiphimada.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Manajemen Transaksi Pengguna", description = "Operasi terkait transaksi pengguna")
@RestController
@RequestMapping("/user")
public class UserTransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private UserService userService;

    @Autowired
    private BarangService barangService;

    @Operation(summary = "Melihat semua barang")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Daftar semua barang",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarangDto.class,
                                    type = "array"))})})
    @GetMapping("/barang")
    public ResponseEntity<List<BarangDto>> getAllItems() {
        List<BarangDto> items = barangService.getAllBarang();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Membuat transaksi baru",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   description = "Transaksi yang akan dibuat",
                   content = @Content(mediaType = "application/json", 
                                      schema = @Schema(implementation = TransaksiDto.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Transaksi berhasil dibuat",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransaksiDto.class))}),
        @ApiResponse(responseCode = "400", 
                description = "Permintaan tidak valid",content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string")))})
    @PostMapping("/transaksi")
    public ResponseEntity<TransaksiDto> createTransaksi(@RequestBody @Valid TransaksiDto transaksiDto) {
        // Log transaksiDto untuk memastikan data yang diterima
        System.out.println("Received TransaksiDto: " + transaksiDto);

        if (transaksiDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        TransaksiDto newTransaksi = transaksiService.createTransaksi(transaksiDto);
        return ResponseEntity.ok(newTransaksi);
    }

    @Operation(summary = "Mendapatkan detail transaksi berdasarkan token",
               responses = {
                   @ApiResponse(responseCode = "200",
                                description = "Detail transaksi berhasil ditemukan",
                                content = {
                                    @Content(mediaType = "application/json", 
                                             schema = @Schema(implementation = TransaksiDto.class))}),
                   @ApiResponse(responseCode = "404", 
                                description = "Transaksi tidak ditemukan",content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string")))})
    @GetMapping("/transaksi/token/{token}")
    public ResponseEntity<TransaksiDto> getTransaksiDetailsByToken(@PathVariable String token) {
        TransaksiDto transaksi = transaksiService.getTransaksiByToken(token);
        return ResponseEntity.ok(transaksi);
    }

    @Operation(summary = "Mendapatkan semua token transaksi untuk pengguna",
               responses = {
                   @ApiResponse(responseCode = "200",
                                description = "Daftar token transaksi ditemukan",
                                content = {
                                    @Content(mediaType = "application/json", 
                                             schema = @Schema(type = "array", 
                                                              implementation = String.class))}),
                   @ApiResponse(responseCode = "404", 
                                description = "Pengguna tidak ditemukan",content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(type = "string")))})
    @GetMapping("/transaksi/tokens")
    public ResponseEntity<List<String>> getAllTokensByUser(Authentication authentication) {
        String currentUserEmail = authentication.getName();
        UserDto user = userService.getUserByEmail(currentUserEmail);
        List<String> tokens = transaksiService.getAllTokensByUser(user.getId());
        return ResponseEntity.ok(tokens);
    }
}
