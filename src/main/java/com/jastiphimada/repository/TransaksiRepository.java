package com.jastiphimada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jastiphimada.entity.Transaksi;
import java.util.List;
import java.util.Optional;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    Optional<Transaksi> findByToken(String token);
    List<Transaksi> findByUserId(Long userId);
}
