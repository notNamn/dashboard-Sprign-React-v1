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
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private User vendedor;

    private LocalDateTime fechaVenta;

    private Double totalVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas;

    // Campo para diferenciar entre venta y proforma
    private String tipoTransaccion;  // Valores posibles: "VENTA", "PROFORMA"
}

