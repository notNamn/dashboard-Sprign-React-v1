package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service;


import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Productos;

import java.util.List;

public interface IProductosServicios  {
    public List<Productos> listarProductos();

    public Productos buscarProductosPorID(Long id);

    public Productos guardarProductos(Productos producto);

    public void eliminarProductos(Productos producto);

    public List<Productos> buscarProductosPorNombre(String nombre);
}
