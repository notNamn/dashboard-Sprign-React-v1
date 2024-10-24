package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model;

import com.dashboardProformaVentas.sistemaProformaVentas.modelo.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "proformas")
public class Proforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProforma;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Client cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private User vendedor;

    private LocalDateTime fechaProforma;

    private Double totalProforma;

    @OneToMany(mappedBy = "proforma", cascade = CascadeType.ALL)
    private List<DetalleProforma> detalleProformas;
}
