package com.jastiphimada.controller;

import com.jastiphimada.dto.AccountRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.jastiphimada.dto.UserDto;
import com.jastiphimada.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.jastiphimada.dto.RegisterRequest;

@Tag(name = "Manajemen Pengguna Admin", description = "Operasi terkait manajemen pengguna oleh admin")
@RestController
@RequestMapping("/admin/users")  // Prefix URL khusus untuk manajemen pengguna
public class AdminAccountController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Melihat semua data pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Daftar semua pengguna",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class, type = "array")) })
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Melihat data pengguna berdasarkan ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data pengguna berdasarkan ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Pengguna tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Mengedit data pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data pengguna berhasil diperbarui",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pengguna tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable Long id, @RequestBody AccountRequest accountRequest) {
        UserDto userDto = UserDto.builder()
                .id(accountRequest.getId()) 
                .name(accountRequest.getName())
                .role(accountRequest.getRole())
                .email(accountRequest.getEmail())
                .password(accountRequest.getPassword())
                .build();
        UserDto updatedUser = userService.updateUserById(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Menghapus akun pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Akun pengguna berhasil dihapus",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pengguna tidak ditemukan",
                    content = @Content)
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User berhasil dihapus.");
    }

    @Operation(summary = "Menambah pengguna baru")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pengguna berhasil ditambahkan",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterRequest registerRequest) {
        UserDto user = UserDto.builder()
                .name(registerRequest.getName())
                .role(registerRequest.getRole())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
        UserDto createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}