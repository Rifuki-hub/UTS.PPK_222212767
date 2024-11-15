package com.jastiphimada.mapper;

import com.jastiphimada.dto.BarangDto;
import com.jastiphimada.dto.HimadaDto;
import com.jastiphimada.entity.Barang;
import com.jastiphimada.entity.Himada;

public class BarangMapper {

   public static Barang mapToBarang(BarangDto barangDto) {
        if (barangDto == null) {
            throw new IllegalArgumentException("BarangDto cannot be null");
        }

        Barang barang = new Barang();
        barang.setBarangId(barangDto.getBarangId());
        barang.setNamaBarang(barangDto.getNamaBarang());
        barang.setHarga(barangDto.getHarga());

        if (barangDto.getHimada() != null) {
            barang.setHimada(mapToHimada(barangDto.getHimada()));
        } else {
            barang.setHimada(null); // Atur himada ke null jika barangDto.getHimada() null
        }

        return barang;
    }

    public static Himada mapToHimada(HimadaDto himadaDto) {
        if (himadaDto == null) {
            return null; // Jika himadaDto null, kembalikan null agar menghindari NullPointerException
        }

        Himada himada = new Himada();
        himada.setHimadaId(himadaDto.getHimadaId());
        himada.setNamaHimada(himadaDto.getNamaHimada());
        return himada;
    }

    public static HimadaDto mapToHimadaDto(Himada himada) {
        if (himada == null) {
            return null; // Atau bisa mengembalikan HimadaDto default
        }
        
        return HimadaDto.builder()
                .himadaId(himada.getHimadaId())
                .namaHimada(himada.getNamaHimada())
                .build();
    }

    public static BarangDto mapToBarangDto(Barang barang) {
        return BarangDto.builder()
                .barangId(barang.getBarangId())
                .namaBarang(barang.getNamaBarang())
                .harga(barang.getHarga())
                .himada(mapToHimadaDto(barang.getHimada())) // Panggil mapToHimadaDto dengan penanganan null
                .build();
    }
}
