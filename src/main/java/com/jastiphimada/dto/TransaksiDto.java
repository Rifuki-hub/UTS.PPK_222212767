package com.jastiphimada.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaksiDto {

    private Long transaksiId;
    private UserDto user;
    private Date tanggalTransaksi;
    private Double totalHarga;
    private String statusPesanan;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Hanya bisa dibaca, tidak bisa ditulis
    private String token;
    
    private List<DetailTransaksiDto> detailTransaksi; // Referensi ke DTO DetailTransaksi
}

