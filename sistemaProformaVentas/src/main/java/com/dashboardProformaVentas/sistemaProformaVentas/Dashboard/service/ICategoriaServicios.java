package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Categoria;

import java.util.List;

public interface ICategoriaServicios {
    public List<Categoria > listarCategorias();

    public Categoria buscarPorId(Long id);

    public Categoria guardarCategoria(Categoria categoria);

    public void eliminarCategoria(Categoria categoria);
}
