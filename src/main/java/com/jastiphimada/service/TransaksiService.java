package com.jastiphimada.service;

import com.jastiphimada.dto.TransaksiDto;
import java.util.List;

public interface TransaksiService {
    TransaksiDto createTransaksi(TransaksiDto transaksiDto);
    TransaksiDto updateTransaksi(Long id, TransaksiDto transaksiDto);
    void deleteTransaksi(Long id);
    TransaksiDto getTransaksiById(Long id);
    List<TransaksiDto> getAllTransaksi();
    TransaksiDto updateStatusTransaksi(Long id, String status);
    TransaksiDto getTransaksiByToken(String token);
    List<String> getAllTokensByUser(Long userId); // Metode baru
}
