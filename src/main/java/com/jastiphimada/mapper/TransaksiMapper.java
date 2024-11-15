package com.jastiphimada.mapper;

import com.jastiphimada.dto.TransaksiDto;
import com.jastiphimada.entity.Transaksi;

import java.util.stream.Collectors;

public class TransaksiMapper {

    public static Transaksi mapToTransaksi(TransaksiDto transaksiDto) {
        if (transaksiDto == null) {
            throw new IllegalArgumentException("TransaksiDto cannot be null");
        }

        Transaksi transaksi = Transaksi.builder()
                .transaksiId(transaksiDto.getTransaksiId())
                .user(UserMapper.mapToUser(transaksiDto.getUser()))
                .totalHarga(transaksiDto.getTotalHarga() != null ? transaksiDto.getTotalHarga() : 0.0)
                .build();

        transaksi.setDetailTransaksi(transaksiDto.getDetailTransaksi().stream()
                .map(DetailTransaksiMapper::mapToDetailTransaksi)
                .collect(Collectors.toList()));

        return transaksi;
    }

    public static TransaksiDto mapToTransaksiDto(Transaksi transaksi) {
        return TransaksiDto.builder()
                .transaksiId(transaksi.getTransaksiId())
                .user(UserMapper.mapToUserDto(transaksi.getUser()))
                .tanggalTransaksi(transaksi.getTanggalTransaksi())
                .totalHarga(transaksi.getTotalHarga())
                .statusPesanan(transaksi.getStatusPesanan())
                .token(transaksi.getToken())
                .detailTransaksi(transaksi.getDetailTransaksi().stream()
                        .map(DetailTransaksiMapper::mapToDetailTransaksiDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

