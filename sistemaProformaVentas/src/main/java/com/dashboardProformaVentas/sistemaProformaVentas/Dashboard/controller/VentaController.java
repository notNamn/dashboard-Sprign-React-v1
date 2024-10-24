package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.controller;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto.VentaDTO;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto.DetalleVentaDTO;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service.VentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")// http://localhost:8080/
@CrossOrigin(origins = "http://localhost:5173/")
public class VentaController {

    private final VentaServicio ventaServicio;

    @Autowired
    public VentaController(VentaServicio ventaServicio) {
        this.ventaServicio = ventaServicio;
    }

    // ======================================
    // Operaciones del Carrito
    // ======================================

    /**
     * Agregar un producto al carrito (en una proforma o venta temporal).
     *
     * @param ventaId     El ID de la venta o proforma (carrito actual).
     * @param detalleDTO  Detalle del producto a agregar al carrito.
     * @return Carrito actualizado.
     */
    @PostMapping("/carrito/{ventaId}/agregar-producto")
    public ResponseEntity<VentaDTO> agregarProductoAlCarrito(
            @PathVariable Long ventaId, @RequestBody DetalleVentaDTO detalleDTO) {

        VentaDTO carritoActualizado = ventaServicio.agregarProductoAlCarrito(ventaId, detalleDTO);
        return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
    }

    /**
     * Editar un producto en el carrito (modificar cantidad).
     *
     * @param ventaId     El ID de la venta o proforma (carrito actual).
     * @param detalleDTO  Detalle del producto a editar en el carrito.
     * @return Carrito actualizado.
     */
    @PutMapping("/carrito/{ventaId}/editar-producto")
    public ResponseEntity<VentaDTO> editarProductoEnCarrito(
            @PathVariable Long ventaId, @RequestBody DetalleVentaDTO detalleDTO) {

        VentaDTO carritoActualizado = ventaServicio.editarProductoEnCarrito(ventaId, detalleDTO);
        return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
    }

    /**
     * Eliminar un producto del carrito.
     *
     * @param ventaId     El ID de la venta o proforma (carrito actual).
     * @param productoId  El ID del producto a eliminar.
     * @return Carrito actualizado.
     */
    @DeleteMapping("/carrito/{ventaId}/eliminar-producto/{productoId}")
    public ResponseEntity<VentaDTO> eliminarProductoDelCarrito(
            @PathVariable Long ventaId, @PathVariable Long productoId) {

        VentaDTO carritoActualizado = ventaServicio.eliminarProductoDelCarrito(ventaId, productoId);
        return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
    }

    /**
     * Recalcular el total del carrito antes de confirmar la venta o proforma.
     *
     * @param ventaId  El ID de la venta o proforma (carrito actual).
     * @return Total actualizado del carrito.
     */
    @GetMapping("/carrito/{ventaId}/recalcular-total")
    public ResponseEntity<Double> recalcularTotalCarrito(@PathVariable Long ventaId) {
        double total = ventaServicio.recalcularTotalCarrito(ventaId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // ======================================
    // Operaciones de Ventas y Proformas
    // ======================================

    /**
     * Registrar una venta (confirmar carrito como venta real).
     *
     * @param ventaDTO Detalles de la venta.
     * @return Venta registrada.
     */
    @PostMapping("/registrar-venta")
    public ResponseEntity<VentaDTO> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        ventaDTO.setTipoTransaccion("VENTA");
        ventaServicio.registrarVenta(ventaDTO);
        return new ResponseEntity<>(ventaDTO, HttpStatus.CREATED);
    }

    /**
     * Registrar una proforma (confirmar carrito como proforma).
     *
     * @param proformaDTO Detalles de la proforma.
     * @return Proforma registrada.
     */
    @PostMapping("/registrar-proforma")
    public ResponseEntity<VentaDTO> registrarProforma(@RequestBody VentaDTO proformaDTO) {
        proformaDTO.setTipoTransaccion("PROFORMA");
        ventaServicio.registrarVenta(proformaDTO);
        return new ResponseEntity<>(proformaDTO, HttpStatus.CREATED);
    }

    // ======================================
    // Historial de Ventas y Proformas
    // ======================================

    /**
     * Obtener el historial completo de ventas y proformas.
     *
     * @return Lista de ventas y proformas.
     */
    @GetMapping("/historial")
    public ResponseEntity<List<VentaDTO>> obtenerHistorialCompleto() {
        List<VentaDTO> historial = ventaServicio.obtenerHistorialCompleto();
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

    /**
     * Obtener el historial de ventas o proformas según el tipo.
     *
     * @param tipo Tipo de transacción (VENTA o PROFORMA).
     * @return Historial filtrado.
     */
    @GetMapping("/historial/{tipo}")
    public ResponseEntity<List<VentaDTO>> obtenerHistorialPorTipo(@PathVariable String tipo) {
        List<VentaDTO> historial = ventaServicio.obtenerHistorialPorTipo(tipo);
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

}
