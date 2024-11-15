package com.jastiphimada.mapper;

import com.jastiphimada.dto.HimadaDto;
import com.jastiphimada.entity.Himada;

public class HimadaMapper {

    public static Himada mapToHimada(HimadaDto himadaDto) {
        return Himada.builder()
                .himadaId(himadaDto.getHimadaId())
                .namaHimada(himadaDto.getNamaHimada())
                .build();
    }

    public static HimadaDto mapToHimadaDto(Himada himada) {
        return HimadaDto.builder()
                .himadaId(himada.getHimadaId())
                .namaHimada(himada.getNamaHimada())
                .build();
    }
}
