package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor  // Agrega este
public class VentaDTO {
    private Long idVenta;
    private Long idVendedor;
    private LocalDateTime fechaVenta;
    private Double totalVenta;
    private String tipoTransaccion;  // "VENTA" o "PROFORMA"
    private List<DetalleVentaDTO> detalleVentas;  // Mapeo explícito de detalles

    // Constructor con parámetros
    public VentaDTO(Long idVenta, List<DetalleVentaDTO> detalleVentas) {
        this.idVenta = idVenta;
        this.detalleVentas = detalleVentas;
    }
}
