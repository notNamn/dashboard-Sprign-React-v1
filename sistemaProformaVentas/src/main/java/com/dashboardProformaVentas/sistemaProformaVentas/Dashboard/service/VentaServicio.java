package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto.DetalleVentaDTO;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto.VentaDTO;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.DetalleVenta;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Productos;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Venta;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository.ProductoRepositorio;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.repository.VentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaServicio {

    private final VentaRepositorio ventaRepositorio;
    private final ProductoRepositorio productoRepositorio;

    // Mapa para simular un carrito de compras en memoria (por usuario, por ejemplo)
    private final Map<Long, List<DetalleVentaDTO>> carrito = new HashMap<>();

    @Autowired
    public VentaServicio(VentaRepositorio ventaRepositorio, ProductoRepositorio productoRepositorio) {
        this.ventaRepositorio = ventaRepositorio;
        this.productoRepositorio = productoRepositorio;
    }

    /**
     * Agrega un producto al carrito de compras.
     *
     * @param ventaId     ID de la venta o proforma actual.
     * @param detalleDTO  Detalle del producto a agregar.
     * @return Carrito actualizado.
     */
    public VentaDTO agregarProductoAlCarrito(Long ventaId, DetalleVentaDTO detalleDTO) {
        List<DetalleVentaDTO> detalles = carrito.getOrDefault(ventaId, List.of());
        detalles.add(detalleDTO);
        carrito.put(ventaId, detalles);

        return new VentaDTO(ventaId, detalles);
    }

    /**
     * Edita un producto en el carrito (cambia la cantidad).
     *
     * @param ventaId     ID de la venta o proforma actual.
     * @param detalleDTO  Detalle del producto a editar.
     * @return Carrito actualizado.
     */
    public VentaDTO editarProductoEnCarrito(Long ventaId, DetalleVentaDTO detalleDTO) {
        List<DetalleVentaDTO> detalles = carrito.getOrDefault(ventaId, List.of());
        for (DetalleVentaDTO detalle : detalles) {
            if (detalle.getIdProducto().equals(detalleDTO.getIdProducto())) {
                detalle.setCantidad(detalleDTO.getCantidad());
            }
        }
        carrito.put(ventaId, detalles);

        return new VentaDTO(ventaId, detalles);
    }

    /**
     * Elimina un producto del carrito de compras.
     *
     * @param ventaId     ID de la venta o proforma actual.
     * @param productoId  ID del producto a eliminar.
     * @return Carrito actualizado.
     */
    public VentaDTO eliminarProductoDelCarrito(Long ventaId, Long productoId) {
        List<DetalleVentaDTO> detalles = carrito.getOrDefault(ventaId, List.of());
        detalles = detalles.stream()
                .filter(detalle -> !detalle.getIdProducto().equals(productoId))
                .collect(Collectors.toList());
        carrito.put(ventaId, detalles);

        return new VentaDTO(ventaId, detalles);
    }

    /**
     * Recalcula el total del carrito de compras.
     *
     * @param ventaId ID de la venta o proforma actual.
     * @return Total del carrito.
     */
    public double recalcularTotalCarrito(Long ventaId) {
        List<DetalleVentaDTO> detalles = carrito.getOrDefault(ventaId, List.of());
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getPrecioUnitario() * detalle.getCantidad())
                .sum();
    }

    /**
     * Registra una venta o proforma.
     *
     * @param ventaDTO Objeto DTO que contiene la información de la venta o proforma.
     * @return La venta registrada.
     */
    @Transactional
    public Venta registrarVenta(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setFechaVenta(ventaDTO.getFechaVenta());
        venta.setTotalVenta(ventaDTO.getTotalVenta());
        venta.setTipoTransaccion(ventaDTO.getTipoTransaccion()); // Venta o Proforma

        List<DetalleVenta> detalles = ventaDTO.getDetalleVentas().stream()
                .map(detalleDTO -> {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setCantidad(detalleDTO.getCantidad());
                    detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
                    detalle.setVenta(venta);

                    // Obtener el producto
                    Productos producto = productoRepositorio.findById(detalleDTO.getIdProducto())
                            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

                    // Si es una venta, verificar stock y reducirlo
                    if ("VENTA".equals(ventaDTO.getTipoTransaccion())) {
                        if (producto.getStock() < detalleDTO.getCantidad()) {
                            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombreProducto());
                        }
                        // Reducir el stock
                        producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                        productoRepositorio.save(producto);
                    }

                    detalle.setProducto(producto);
                    return detalle;
                }).collect(Collectors.toList());

        venta.setDetalleVentas(detalles);
        ventaRepositorio.save(venta);
        return venta;
    }

    /**
     * Obtiene el historial de ventas o proformas, basado en el tipo de transacción.
     *
     * @param tipoTransaccion "VENTA" o "PROFORMA"
     * @return Lista de ventas o proformas.
     */
    public List<VentaDTO> obtenerHistorialPorTipo(String tipoTransaccion) {
        List<Venta> ventas = ventaRepositorio.findByTipoTransaccion(tipoTransaccion);
        return ventas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Obtiene el historial completo de ventas y proformas combinadas.
     *
     * @return Lista de ventas y proformas.
     */
    public List<VentaDTO> obtenerHistorialCompleto() {
        List<Venta> ventas = ventaRepositorio.findAll();
        return ventas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Mapeo de entidades a DTOs

    private VentaDTO mapToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setIdVenta(venta.getIdVenta());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTotalVenta(venta.getTotalVenta());
        dto.setTipoTransaccion(venta.getTipoTransaccion());

        List<DetalleVentaDTO> detallesDTO = venta.getDetalleVentas().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());
        dto.setDetalleVentas(detallesDTO);

        return dto;
    }

    private DetalleVentaDTO mapDetalleToDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setIdProducto(detalle.getProducto().getIdProducto());
        dto.setNombreProducto(detalle.getProducto().getNombreProducto());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        return dto;
    }
}
