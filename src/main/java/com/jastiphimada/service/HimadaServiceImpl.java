package com.jastiphimada.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jastiphimada.dto.HimadaDto;
import com.jastiphimada.entity.Himada;
import com.jastiphimada.mapper.HimadaMapper;
import com.jastiphimada.repository.HimadaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HimadaServiceImpl implements HimadaService {

    @Autowired
    private HimadaRepository himadaRepository;

    @Override
    public HimadaDto createHimada(HimadaDto himadaDto) {
        Himada himada = HimadaMapper.mapToHimada(himadaDto);
        Himada newHimada = himadaRepository.save(himada);
        return HimadaMapper.mapToHimadaDto(newHimada);
    }

    @Override
    public HimadaDto updateHimada(Long id, HimadaDto himadaDto) {
        Himada himada = himadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Himada not found"));

        himada.setNamaHimada(himadaDto.getNamaHimada());

        Himada updatedHimada = himadaRepository.save(himada);
        return HimadaMapper.mapToHimadaDto(updatedHimada);
    }

    @Override
    public void deleteHimada(Long id) {
        himadaRepository.deleteById(id);
    }

    @Override
    public HimadaDto getHimadaById(Long id) {
        Himada himada = himadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Himada not found"));
        return HimadaMapper.mapToHimadaDto(himada);
    }

    @Override
    public List<HimadaDto> getAllHimada() {
        List<Himada> himadaList = himadaRepository.findAll();
        return himadaList.stream()
                .map(HimadaMapper::mapToHimadaDto)
                .collect(Collectors.toList());
    }
}
