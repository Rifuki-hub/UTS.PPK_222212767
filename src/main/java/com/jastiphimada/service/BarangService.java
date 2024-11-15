package com.jastiphimada.service;

import com.jastiphimada.dto.BarangDto;
import java.util.List;

public interface BarangService {
    List<BarangDto> getAllBarang();
    BarangDto getBarangById(Long id);
    BarangDto createBarang(BarangDto barangDto);
    BarangDto updateBarang(Long id, BarangDto barangDto);
    void deleteBarang(Long id);
    List<BarangDto> getBarangByHimada(Long himadaId);
}
