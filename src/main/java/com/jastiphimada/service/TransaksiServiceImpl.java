package com.jastiphimada.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jastiphimada.dto.TransaksiDto;
import com.jastiphimada.entity.Barang;
import com.jastiphimada.entity.Transaksi;
import com.jastiphimada.mapper.TransaksiMapper;
import com.jastiphimada.repository.BarangRepository;
import com.jastiphimada.repository.TransaksiRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransaksiServiceImpl implements TransaksiService {

    @Autowired
    private TransaksiRepository transaksiRepository;
    @Autowired
    private BarangRepository barangRepository;

    @Override
    public TransaksiDto createTransaksi(TransaksiDto transaksiDto) {
        if (transaksiDto == null) {
            throw new IllegalArgumentException("TransaksiDto cannot be null");
        }

        Transaksi transaksi = TransaksiMapper.mapToTransaksi(transaksiDto);
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setStatusPesanan("Pending");

        // Hitung total harga
        double totalHarga = transaksi.getDetailTransaksi().stream()
                .mapToDouble(detail -> {
                    Barang barang = barangRepository.findById(detail.getBarang().getBarangId())
                            .orElseThrow(() -> new IllegalArgumentException("Barang dengan ID " + detail.getBarang().getBarangId() + " tidak ditemukan"));
                    return barang.getHarga() * detail.getJumlah();
                })
                .sum();
        transaksi.setTotalHarga(totalHarga);

        // Set objek transaksi dalam setiap DetailTransaksi
        transaksi.getDetailTransaksi().forEach(detail -> detail.setTransaksi(transaksi));

        Transaksi newTransaksi = transaksiRepository.save(transaksi);
        return TransaksiMapper.mapToTransaksiDto(newTransaksi);
    }

    @Override
    public TransaksiDto updateTransaksi(Long id, TransaksiDto transaksiDto) {
        Transaksi transaksi = transaksiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaksi not found"));

        // Update total harga jika detail transaksi berubah
        double totalHarga = transaksiDto.getDetailTransaksi().stream()
                .mapToDouble(detail -> detail.getBarang().getHarga() * detail.getJumlah())
                .sum();
        transaksi.setTotalHarga(totalHarga);
        transaksi.setStatusPesanan(transaksiDto.getStatusPesanan()); // Status bisa diubah oleh admin

        Transaksi updatedTransaksi = transaksiRepository.save(transaksi);
        return TransaksiMapper.mapToTransaksiDto(updatedTransaksi);
    }

    @Override
    public void deleteTransaksi(Long id) {
        transaksiRepository.deleteById(id);
    }

    @Override
    public TransaksiDto getTransaksiById(Long id) {
        Transaksi transaksi = transaksiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaksi not found"));
        return TransaksiMapper.mapToTransaksiDto(transaksi);
    }

    @Override
    public List<TransaksiDto> getAllTransaksi() {
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        return transaksiList.stream()
                .map(TransaksiMapper::mapToTransaksiDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransaksiDto updateStatusTransaksi(Long id, String status) {
        Transaksi transaksi = transaksiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaksi not found"));

        transaksi.setStatusPesanan(status); // Admin dapat mengubah status pesanan
        Transaksi updatedTransaksi = transaksiRepository.save(transaksi);
        return TransaksiMapper.mapToTransaksiDto(updatedTransaksi);
    }

    @Override
    public TransaksiDto getTransaksiByToken(String token) {
        Transaksi transaksi = transaksiRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Transaksi not found"));
        return TransaksiMapper.mapToTransaksiDto(transaksi);
    }

    @Override
    public List<String> getAllTokensByUser(Long userId) {
        List<Transaksi> transaksiList = transaksiRepository.findByUserId(userId);
        return transaksiList.stream()
                .map(Transaksi::getToken)
                .collect(Collectors.toList());
    }
}
