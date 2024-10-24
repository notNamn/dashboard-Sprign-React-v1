import React, { useState, useEffect } from 'react';
import {
  Typography, Paper, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Button, Dialog, DialogTitle, DialogContent,
  DialogActions, TextField, MenuItem, Select, InputLabel, FormControl
} from '@mui/material';
import { useProducts } from '../../hooks/useProducts';
import { useCategories } from '../../hooks/useCategories';

export const Products = () => {
  const { products, loading, error, addProduct, updateProduct, deleteProduct } = useProducts();
  const { categories, fetchCategories, addCategory } = useCategories();
  const [open, setOpen] = useState(false);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [newCategory, setNewCategory] = useState("");

  // Cargar categorías cuando el componente se monta
  useEffect(() => {
    fetchCategories();
  }, []);

  // Abrir la ventana modal, con un producto nuevo o existente
  const handleOpen = (product) => {
    setCurrentProduct(
      product
        ? { ...product, categoriaId: product.categoria.idCategoria }
        : { nombreProducto: '', precio: '', stock: '', categoriaId: '' }
    );
    setOpen(true);
  };

  // Cerrar la ventana modal y resetear los estados
  const handleClose = () => {
    setOpen(false);
    setCurrentProduct(null);
    setNewCategory('');
  };

  // Guardar los cambios (agregar o actualizar producto)
  const handleSave = async (event) => {
    event.preventDefault();
    try {
      if (newCategory) {
        // Si se ha añadido una nueva categoría
        const createdCategory = await addCategory({ nombreCategoria: newCategory });
        currentProduct.categoriaId = createdCategory.idCategoria;
      }

      if (currentProduct.idProducto) {
        // Si el producto ya tiene ID, lo actualizamos
        await updateProduct(currentProduct.idProducto, currentProduct);
      } else {
        // Si no tiene ID, lo creamos
        await addProduct(currentProduct);
      }
      handleClose();
    } catch (error) {
      console.error("Error al guardar el producto:", error);
    }
  };

  // Eliminar un producto
  const handleDelete = async (idProducto) => {
    try {
      await deleteProduct(idProducto);
    } catch (error) {
      console.error("Error al eliminar el producto:", error);
    }
  };

  return (
    <>
      <Typography variant="h4" gutterBottom>Productos</Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpen(null)} style={{ marginBottom: '20px' }}>
        Agregar Producto
      </Button>

      {loading ? (
        <Typography variant="h6">Cargando productos...</Typography>
      ) : error ? (
        <Typography color="error">{error}</Typography>
      ) : (
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nombre</TableCell>
                <TableCell align="right">Precio</TableCell>
                <TableCell align="right">Stock</TableCell>
                <TableCell align="right">Categoría</TableCell>
                <TableCell align="right">Acciones</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {products.map((product) => (
                <TableRow key={product.idProducto}>
                  <TableCell>{product.nombreProducto}</TableCell>
                  <TableCell align="right">${product.precio.toFixed(2)}</TableCell>
                  <TableCell align="right">{product.stock}</TableCell>
                  <TableCell align="right">{product.categoria.nombreCategoria}</TableCell>
                  <TableCell align="right">
                    <Button onClick={() => handleOpen(product)}>Editar</Button>
                    <Button onClick={() => handleDelete(product.idProducto)} color="secondary">Eliminar</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{currentProduct?.idProducto ? 'Editar Producto' : 'Agregar Producto'}</DialogTitle>
        <form onSubmit={handleSave}>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              label="Nombre"
              type="text"
              fullWidth
              value={currentProduct?.nombreProducto || ''}
              onChange={(e) => setCurrentProduct({ ...currentProduct, nombreProducto: e.target.value })}
            />
            <TextField
              margin="dense"
              label="Precio"
              type="number"
              fullWidth
              value={currentProduct?.precio || ''}
              onChange={(e) => setCurrentProduct({ ...currentProduct, precio: parseFloat(e.target.value) })}
            />
            <TextField
              margin="dense"
              label="Stock"
              type="number"
              fullWidth
              value={currentProduct?.stock || ''}
              onChange={(e) => setCurrentProduct({ ...currentProduct, stock: parseInt(e.target.value) })}
            />
            <FormControl fullWidth margin="dense">
              <InputLabel>Categoría</InputLabel>
              <Select
                value={currentProduct?.categoriaId || ''}
                onChange={(e) => setCurrentProduct({ ...currentProduct, categoriaId: e.target.value })}
              >
                {categories.map((category) => (
                  <MenuItem key={category.idCategoria} value={category.idCategoria}>
                    {category.nombreCategoria}
                  </MenuItem>
                ))}
                <MenuItem value="">
                  <em>Agregar nueva categoría</em>
                </MenuItem>
              </Select>
            </FormControl>

            {currentProduct?.categoriaId === "" && (
              <TextField
                margin="dense"
                label="Nueva Categoría"
                type="text"
                fullWidth
                value={newCategory}
                onChange={(e) => setNewCategory(e.target.value)}
              />
            )}
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancelar</Button>
            <Button type="submit" color="primary">Guardar</Button>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
};
