package com.jastiphimada.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private Long id;
    private String name;
    private String role;
    private String email;
    private String password;
}
