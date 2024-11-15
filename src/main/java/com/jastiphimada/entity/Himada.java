package com.jastiphimada.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "himada")
public class Himada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long himadaId;

    private String namaHimada;
}
