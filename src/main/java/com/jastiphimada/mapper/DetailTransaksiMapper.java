package com.jastiphimada.mapper;

import com.jastiphimada.dto.DetailTransaksiDto;
import com.jastiphimada.entity.DetailTransaksi;

public class DetailTransaksiMapper {

    public static DetailTransaksi mapToDetailTransaksi(DetailTransaksiDto detailDto) {
        if (detailDto == null || detailDto.getBarang() == null || detailDto.getBarang().getBarangId() == null) {
            throw new IllegalArgumentException("DetailTransaksiDto or Barang cannot be null");
        }

        DetailTransaksi detail = new DetailTransaksi();
        detail.setBarang(BarangMapper.mapToBarang(detailDto.getBarang()));
        detail.setJumlah(detailDto.getJumlah());
        return detail;
    }

    public static DetailTransaksiDto mapToDetailTransaksiDto(DetailTransaksi detailTransaksi) {
        return DetailTransaksiDto.builder()
                .detailTransaksiId(detailTransaksi.getDetailTransaksiId())
                .barang(BarangMapper.mapToBarangDto(detailTransaksi.getBarang())) // Menggunakan DTO Barang
                .jumlah(detailTransaksi.getJumlah())
                .build();
    }
}
