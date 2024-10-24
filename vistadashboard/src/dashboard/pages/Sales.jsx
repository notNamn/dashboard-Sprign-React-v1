import React, { useState, useEffect } from 'react';
import {
  Button, Dialog, DialogTitle, DialogContent, TextField, Table, TableBody, TableCell,
  TableHead, TableRow, Typography, TableContainer, Paper
} from '@mui/material';
import { PlusCircle } from 'lucide-react';
import { useSales } from '../../hooks/useSales';

export const Sales = () => {
  const { products, cart, fetchProducts, addToCart, updateQuantity, calculateTotal, registerSale } = useSales();
  const [open, setOpen] = useState(false);
  const [search, setSearch] = useState('');
  const [ventaId, setVentaId] = useState(''); // ID de la venta (puedes cambiarlo según tu lógica)
  const [total, setTotal] = useState(0);

  // Actualiza el total cada vez que el carrito cambia
  useEffect(() => {
    if (ventaId) {
      calculateTotal(ventaId).then(total => setTotal(total));
    }
  }, [cart, ventaId, calculateTotal]);

  // Buscar productos mientras escribe
  const handleSearch = (e) => {
    setSearch(e.target.value);
    fetchProducts(e.target.value);
  };

  const handleAddProduct = (product) => {
    addToCart(ventaId, product);
  };

  const handleConfirmSale = async () => {
    const ventaRegistrada = await registerSale(ventaId);
    console.log('Venta confirmada:', ventaRegistrada);
  };

  return (
    <>
      <Button variant="contained" onClick={() => setOpen(true)}>
        Nueva compra
      </Button>

      <Dialog open={open} onClose={() => setOpen(false)} fullWidth maxWidth="md">
        <DialogTitle>Nueva Compra</DialogTitle>
        <DialogContent>
          <TextField
            label="Buscar producto"
            variant="outlined"
            fullWidth
            value={search}
            onChange={handleSearch}
            placeholder="Ingrese nombre de producto"
            margin="dense"
          />

          <Typography variant="h6" gutterBottom>
            Resultados de búsqueda:
          </Typography>
          {products.length > 0 ? (
            products.map((product) => (
              <div key={product.idProducto} style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '10px 0' }}>
                <Typography>
                  {product.nombreProducto} - {product.categoria?.nombreCategoria || 'Sin categoría'}
                </Typography>
                <Button
                  variant="outlined"
                  startIcon={<PlusCircle />}
                  onClick={() => handleAddProduct(product)}
                >
                  {console.log( "se preciono" + product)
                    }
                  Agregar
                </Button>
              </div>
            ))
          ) : (
            <Typography>No se encontraron productos</Typography>
          )}

          <TableContainer component={Paper} style={{ marginTop: '20px' }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Producto</TableCell>
                  <TableCell>Cantidad</TableCell>
                  <TableCell>Precio Unitario</TableCell>
                  <TableCell>Total</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {cart.map((item) => (
                  <TableRow key={item.idProducto}>
                    <TableCell>{item.nombreProducto}</TableCell>
                    <TableCell>
                      <TextField
                        type="number"
                        value={item.cantidad}
                        onChange={(e) => updateQuantity(ventaId, item.idProducto, e.target.value)}
                        inputProps={{ min: 1 }}
                      />
                    </TableCell>
                    <TableCell>${item.precio.toFixed(2)}</TableCell>
                    <TableCell>${(item.precio * item.cantidad).toFixed(2)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>

          <Typography variant="h6" style={{ marginTop: '20px' }}>
            Total: ${total.toFixed(2)}
          </Typography>

          <Button variant="contained" color="primary" fullWidth style={{ marginTop: '20px' }} onClick={handleConfirmSale}>
            Confirmar Compra
          </Button>
        </DialogContent>
      </Dialog>
    </>
  );
};
