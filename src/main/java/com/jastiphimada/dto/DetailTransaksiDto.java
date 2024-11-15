package com.jastiphimada.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailTransaksiDto {

    private Long detailTransaksiId;
    private TransaksiDto transaksi;
    private BarangDto barang;
    private Integer jumlah;

}
