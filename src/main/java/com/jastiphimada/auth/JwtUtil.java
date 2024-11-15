package com.jastiphimada.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil implements Serializable {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRE_DURATION;

    // Generate access token dengan role dalam bentuk list
    public String generateAccessToken(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(authentication.getName()) // Menyimpan email pengguna
                .claim("roles", roles) // Menyimpan roles dalam bentuk list (array)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Validasi apakah token valid atau tidak
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT is not supported: " + ex.getMessage());
        } catch (SignatureException ex) {
            System.out.println("Signature validation failed: " + ex.getMessage());
        }
        return false;
    }

    // Mendapatkan email pengguna dari token
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    // Mendapatkan roles dari token (mengubah klaim roles menjadi List)
    public List<String> getRoles(String token) {
        Claims claims = parseClaims(token);
        List<String> roles = (List<String>) claims.get("roles");
        return roles;
    }

    // Memeriksa apakah token memiliki peran tertentu
    public boolean hasRole(String token, String role) {
        List<String> roles = getRoles(token);
        return roles.contains(role);
    }

    // Mengambil klaim dari token
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Mendapatkan username dari token
    public String extractUsername(String token) {
        return getSubject(token);
    }

    // Validasi token dengan user details
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && validateAccessToken(token));
    }
}
