package com.jastiphimada.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarangDto {

    private Long barangId;
    private String namaBarang;
    private Double harga;
    private HimadaDto himada; 

}
