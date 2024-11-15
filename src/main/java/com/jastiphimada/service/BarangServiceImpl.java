package com.jastiphimada.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jastiphimada.dto.BarangDto;
import com.jastiphimada.entity.Barang;
import com.jastiphimada.mapper.BarangMapper;
import com.jastiphimada.repository.BarangRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarangServiceImpl implements BarangService {

    @Autowired
    private BarangRepository barangRepository;

    @Override
    public List<BarangDto> getAllBarang() {
        List<Barang> barangs = barangRepository.findAll();
        return barangs.stream()
                .map(BarangMapper::mapToBarangDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public BarangDto getBarangById(Long id) {
        Barang barang = barangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang not found"));
        return BarangMapper.mapToBarangDto(barang);
    }

    @Override
    public BarangDto createBarang(BarangDto barangDto) {
        Barang barang = BarangMapper.mapToBarang(barangDto);
        Barang newBarang = barangRepository.save(barang);
        return BarangMapper.mapToBarangDto(newBarang);
    }

    @Override
    public BarangDto updateBarang(Long id, BarangDto barangDto) {
        Barang barang = barangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang not found"));

        barang.setNamaBarang(barangDto.getNamaBarang());
        barang.setHarga(barangDto.getHarga());
        barang.setHimada(BarangMapper.mapToHimada(barangDto.getHimada())); // Ensure Himada is set correctly

        Barang updatedBarang = barangRepository.save(barang);
        return BarangMapper.mapToBarangDto(updatedBarang);
    }

    @Override
    public void deleteBarang(Long id) {
        barangRepository.deleteById(id);
    }

    @Override
    public List<BarangDto> getBarangByHimada(Long himadaId) {
        List<Barang> barangs = barangRepository.findByHimada_HimadaId(himadaId); // Menggunakan metode yang benar
        return barangs.stream()
                .map(BarangMapper::mapToBarangDto)
                .collect(Collectors.toList());
    }
}
