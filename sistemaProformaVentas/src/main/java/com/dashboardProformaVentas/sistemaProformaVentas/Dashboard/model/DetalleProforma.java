package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_proforma")
@Getter
@Setter
@NoArgsConstructor
public class DetalleProforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_proforma")
    private Proforma proforma;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Productos producto;

    private Integer cantidad;

    private Double precioUnitario;

}
