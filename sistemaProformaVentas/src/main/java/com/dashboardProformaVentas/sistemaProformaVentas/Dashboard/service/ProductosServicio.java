package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Productos;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosServicio implements IProductosServicios{

    @Autowired
    private ProductoRepositorio productosRepositorio;

    @Override
    public List<Productos> listarProductos() {
        return productosRepositorio.findAll();
    }

    @Override
    public Productos buscarProductosPorID(Long id) {
        return productosRepositorio.findById(id).orElse(null);
    }

    @Override
    public Productos guardarProductos(Productos producto) {
        return productosRepositorio.save(producto);
    }

    @Override
    public void eliminarProductos(Productos producto) {
        productosRepositorio.delete(producto);
    }

    @Override
    public List<Productos> buscarProductosPorNombre(String nombre) {
        return productosRepositorio.findByNombreProductoContainingIgnoreCase(nombre);
    }

}
