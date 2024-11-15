package com.jastiphimada.dto;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {

    private Long id;
    private String name;
    private String role;
    private String email;
    private String password;

    public UserDto(Long id) {
        this.id = id;
    }
    
    @Override
    @JsonSerialize(using = ToStringSerializer.class)  // Menambahkan serialisasi pada otoritas
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Pastikan role tidak null dan sesuai dengan format yang diinginkan
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
