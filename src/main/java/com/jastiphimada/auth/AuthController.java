package com.jastiphimada.auth;

import com.jastiphimada.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jastiphimada.dto.UserDto;
import com.jastiphimada.exception.EmailAlreadyExistsException;
import com.jastiphimada.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Sistem Autentikasi", description = "Operasi terkait autentikasi pengguna")
@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @Operation(summary = "Login untuk mendapatkan token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login sukses",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Login gagal",
                content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            String accessToken = jwtUtil.generateAccessToken(authentication);
            UserDto user = userService.getUserByEmail(request.getEmail());
            String role = authentication.getAuthorities().stream()
                    .findFirst().get().getAuthority();
            AuthResponse response = new AuthResponse(request.getEmail(), accessToken, role);

            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Register untuk membuat akun baru")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registrasi sukses",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
        @ApiResponse(responseCode = "400", description = "Email sudah terdaftar",
                content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            UserDto user = UserDto.builder() 
                    .name(request.getName()) 
                    .role(request.getRole()) 
                    .email(request.getEmail()) 
                    .password(request.getPassword()) .build(); 
            UserDto createdUser = userService.createUser(user); 
            return ResponseEntity.ok().body(createdUser);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
