package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VentaRepositorio extends JpaRepository<Venta, Long> {

    // Para obtener solo ventas
    List<Venta> findByTipoTransaccion(String tipoTransaccion);
}
