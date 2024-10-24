package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepositorio extends JpaRepository <Categoria, Long> {
}
