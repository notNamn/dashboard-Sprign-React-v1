package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Productos, Long> {
    List<Productos> findByNombreProductoContainingIgnoreCase(String nombre);

}
