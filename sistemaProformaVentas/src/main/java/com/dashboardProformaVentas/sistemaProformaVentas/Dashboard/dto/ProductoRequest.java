package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRequest {
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private Integer stock;

    // para buscar una categoria existente
    private Long categoriaId;

    // para crear una categoria si es necesario
    private String categoriaNombre;
}
