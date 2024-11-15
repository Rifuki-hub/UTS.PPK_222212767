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
@Table(name = "barang")
public class Barang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long barangId;

    private String namaBarang;
    private Double harga;

    @ManyToOne
    @JoinColumn(name = "himada_id")
    private Himada himada;
}
