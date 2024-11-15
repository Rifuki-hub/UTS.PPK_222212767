package com.jastiphimada.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
@Table(name = "transaksi")
public class Transaksi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaksiId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @Temporal(TemporalType.DATE)
    private Date tanggalTransaksi = new Date(); // Set default tanggalTransaksi
    
    private Double totalHarga;
    
    @Builder.Default
    private String statusPesanan = "Pending"; // Set default statusPesanan

    @Column(unique = true, updatable = false)
    private String token;

    @JsonManagedReference
    @OneToMany(mappedBy = "transaksi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetailTransaksi> detailTransaksi;

    @PrePersist
    public void prePersist() {
        if (token == null) {
            token = UUID.randomUUID().toString();
        }
    }
}
