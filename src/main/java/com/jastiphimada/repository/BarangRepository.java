package com.jastiphimada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jastiphimada.entity.Barang;
import java.util.List;

public interface BarangRepository extends JpaRepository<Barang, Long> {
    List<Barang> findByHimada_HimadaId(Long himadaId); // Menggunakan Himada.himadaId
}
