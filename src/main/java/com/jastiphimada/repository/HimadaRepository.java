package com.jastiphimada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jastiphimada.entity.Himada;
import java.util.List;

public interface HimadaRepository extends JpaRepository<Himada, Long> {
    List<Himada> findByHimadaId(Long himadaId);
}
