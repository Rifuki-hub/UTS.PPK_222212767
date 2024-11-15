package com.jastiphimada.service;

import com.jastiphimada.dto.HimadaDto;
import java.util.List;

public interface HimadaService {
    HimadaDto createHimada(HimadaDto himadaDto);
    HimadaDto updateHimada(Long id, HimadaDto himadaDto);
    void deleteHimada(Long id);
    HimadaDto getHimadaById(Long id);
    List<HimadaDto> getAllHimada();
}
