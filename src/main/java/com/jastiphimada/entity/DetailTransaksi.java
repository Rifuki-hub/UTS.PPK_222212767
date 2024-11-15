package com.jastiphimada.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "detail_transaksi")
public class DetailTransaksi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailTransaksiId;

    @JsonBackReference  // Sisi yang tidak diserialisasi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaksi_id")
    private Transaksi transaksi;

    @ManyToOne
    @JoinColumn(name = "barang_id")
    private Barang barang;

    private Integer jumlah;
}