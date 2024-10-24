package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Categoria;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio implements ICategoriaServicios {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepositorio.findAll();
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return categoriaRepositorio.findById(id).orElse(null);
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        categoriaRepositorio.delete(categoria);
    }
}
