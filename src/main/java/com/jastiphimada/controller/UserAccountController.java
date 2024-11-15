package com.jastiphimada.controller;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.jastiphimada.dto.ChangePasswordRequest;
import com.jastiphimada.dto.AccountRequest;
import com.jastiphimada.dto.UserDto;
import com.jastiphimada.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(name = "Manajemen Akun Pengguna", description = "Operasi terkait akun pengguna")
@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Mendapatkan detail profil pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detail profil pengguna",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) })
    })
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserDetails(Authentication authentication) {
        String email = authentication.getName();
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Mengedit profil pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil pengguna berhasil diperbarui",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Akses dilarang",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Request tidak valid",
                    content = @Content)
    })
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody AccountRequest accountRequest, Authentication authentication) {
        String currentUserEmail = authentication.getName();
        UserDto user = userService.getUserByEmail(currentUserEmail);
        if (!user.getId().equals(accountRequest.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Anda hanya dapat mengedit data Anda sendiri.");
        }
        UserDto userDto = UserDto.builder()
                .id(user.getId())  // Menjaga ID tetap sama
                .name(accountRequest.getName())
                .role(accountRequest.getRole())
                .email(accountRequest.getEmail())
                .password(accountRequest.getPassword())
                .build();
        UserDto updatedUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Mengganti password pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password berhasil diubah",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Request tidak valid atau password lama salah",
                    content = @Content)
    })
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        String currentUserEmail = authentication.getName();

        boolean isOldPasswordValid = userService.verifyOldPassword(currentUserEmail, changePasswordRequest.getOldPassword());
        if (!isOldPasswordValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password lama salah.");
        }
        userService.changePassword(currentUserEmail, changePasswordRequest.getNewPassword());
        return ResponseEntity.ok("Password berhasil diubah.");
    }

    @Operation(summary = "Menghapus akun pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Akun berhasil dihapus",
                    content = @Content)
    })
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        String currentUserEmail = authentication.getName();
        userService.deleteUserByEmail(currentUserEmail);
        return ResponseEntity.ok("Akun berhasil dihapus.");
    }
}