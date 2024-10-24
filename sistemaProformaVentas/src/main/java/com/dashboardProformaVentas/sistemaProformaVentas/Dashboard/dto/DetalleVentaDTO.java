package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaDTO {
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
}
