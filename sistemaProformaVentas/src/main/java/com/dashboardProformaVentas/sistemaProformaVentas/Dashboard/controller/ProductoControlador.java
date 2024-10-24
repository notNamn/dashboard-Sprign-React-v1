package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.controller;

import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.dto.ProductoRequest;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.exepcion.RecursosNoEntradoExepcion;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Categoria;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.model.Productos;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service.ICategoriaServicios;
import com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.service.IProductosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductoControlador {

    @Autowired
    private IProductosServicios productosServicios;

    @Autowired
    private ICategoriaServicios categoriaServicios;

    @GetMapping("/productos")
    public List<Productos> obtenerProductos(){
        return productosServicios.listarProductos();
    }

    @PostMapping("/productos")
    public ResponseEntity<Productos> agregarProducto(@RequestBody ProductoRequest request){
        Categoria categoria;

        if (request.getCategoriaId() != null){
            categoria = categoriaServicios.buscarPorId(request.getCategoriaId());
            if (categoria == null){
                throw new RecursosNoEntradoExepcion("Categoria no encontrada en la BD" +
                        request.getCategoriaId());
            }
        }else if (request.getCategoriaNombre() != null &&
                !request.getCategoriaNombre().isEmpty()){
            // si no hay ID, buscar por nombre o  crear uno nuevo
            categoria = categoriaServicios.listarCategorias().stream()
                    .filter(cat -> cat.getNombreCategoria().equalsIgnoreCase(request.getCategoriaNombre()))
                    .findFirst().orElseGet(()->{
                        Categoria nuevaCategoria = new Categoria();
                        nuevaCategoria.setNombreCategoria(request.getCategoriaNombre());
                        return  categoriaServicios.guardarCategoria(nuevaCategoria);
                    });
        }else {
            throw new RecursosNoEntradoExepcion("Se requiere una categoría para el producto");
        }

        // crear el producto
        Productos nuevoProducto = new Productos();
        nuevoProducto.setNombreProducto(request.getNombreProducto());
        nuevoProducto.setDescripcion(request.getDescripcion());
        nuevoProducto.setPrecio(request.getPrecio());
        nuevoProducto.setStock(request.getStock());
        nuevoProducto.setCategoria(categoria);

        Productos productosGuardados = productosServicios.guardarProductos(nuevoProducto);
        return ResponseEntity.ok(productosGuardados);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Productos> obtenerProductosPorId(@PathVariable Long id){
        Productos producto = productosServicios.buscarProductosPorID(id);
        if (producto == null){
            throw new RecursosNoEntradoExepcion("Producto no encontrado con ID: " +
                    id);
        }
        return  ResponseEntity.ok(producto);
    }


    @PutMapping("/productos/{id}")
    public ResponseEntity<Productos> actualizarProducto(@PathVariable long id,
                                                        @RequestBody ProductoRequest request) {
        Productos producto = productosServicios.buscarProductosPorID(id);
        if (producto == null) {
            throw new RecursosNoEntradoExepcion("Producto no encontrado con ID: " + id);
        }

        // Asignar los campos del request
        producto.setNombreProducto(request.getNombreProducto());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(obtenerOCrearCategoria(request));  // Método auxiliar para obtener/crear categoría

        Productos productoActualizado = productosServicios.guardarProductos(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable long id) {
        Productos producto = productosServicios.buscarProductosPorID(id);
        if (producto == null) {
            throw new RecursosNoEntradoExepcion("Producto no encontrado con ID: " + id);
        }
        productosServicios.eliminarProductos(producto);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria nuevaCategoria) {
        Categoria categoriaGuardada = categoriaServicios.guardarCategoria(nuevaCategoria);
        return ResponseEntity.ok(categoriaGuardada);
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return categoriaServicios.listarCategorias();
    }

    @GetMapping("/productosVentas")
    public List<Productos> obtenerProductosPorNombre(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return productosServicios.buscarProductosPorNombre(search); // Implementar este método en el servicio
        } else {
            return productosServicios.listarProductos();
        }
    }



    private Categoria obtenerOCrearCategoria(ProductoRequest request) {
        Categoria categoria = null;
        if (request.getCategoriaId() != null) {
            categoria = categoriaServicios.buscarPorId(request.getCategoriaId());
            if (categoria == null) {
                throw new RecursosNoEntradoExepcion("Categoría no encontrada con ID: " + request.getCategoriaId());
            }
        } else if (request.getCategoriaNombre() != null && !request.getCategoriaNombre().isEmpty()) {
            categoria = categoriaServicios.listarCategorias().stream()
                    .filter(cat -> cat.getNombreCategoria().equalsIgnoreCase(request.getCategoriaNombre()))
                    .findFirst()
                    .orElseGet(() -> {
                        Categoria nuevaCategoria = new Categoria();
                        nuevaCategoria.setNombreCategoria(request.getCategoriaNombre());
                        return categoriaServicios.guardarCategoria(nuevaCategoria);
                    });
        } else {
            throw new RecursosNoEntradoExepcion("Se requiere una categoría para el producto.");
        }
        return categoria;
    }

}