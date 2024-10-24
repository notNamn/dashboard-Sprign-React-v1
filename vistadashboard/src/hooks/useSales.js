import { useState } from 'react'; 
import axios from 'axios';

export const useSales = () => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const urlProductos = "http://localhost:8080/dashboard/productosVentas"; // Asegúrate de usar la URL correcta de productos
  const urlBase = "http://localhost:8080/dashboard"; // URL para las rutas del controlador de ventas

  // Función para buscar productos por nombre
  const fetchProducts = async (query) => {
    setLoading(true);
    try {
      const response = await axios.get(`${urlProductos}?search=${query}`);
      setProducts(Array.isArray(response.data) ? response.data : []);
      console.log(response.data); // borrar
    } catch (err) {
      setError('Error al buscar productos');
    } finally {
      setLoading(false);
    }
  };

  // Función para agregar un producto al carrito en el backend
  const addToCart = async (ventaId, product) => {
    try {
      const response = await axios.post(`${urlBase}/carrito/${ventaId}/agregar-producto`, {
        productoId: product.idProducto, // Asegúrate de usar el campo correcto
        cantidad: 1,
        precio: product.precio
      });
      setCart(response.data.detalles);
    } catch (err) {
      setError('Error al agregar producto al carrito');
      console.error(err); // Para obtener más detalles del error
    }
  };
  

  // Función para actualizar la cantidad de un producto en el carrito en el backend
  const updateQuantity = async (ventaId, productId, quantity) => {
    try {
      const response = await axios.put(`${urlBase}/carrito/${ventaId}/editar-producto`, {
        productoId: productId,
        cantidad: quantity
      });
      setCart(response.data.detalles);
    } catch (err) {
      setError('Error al actualizar cantidad');
    }
  };

  // Función para eliminar un producto del carrito en el backend
  const removeFromCart = async (ventaId, productId) => {
    try {
      const response = await axios.delete(`${urlBase}/carrito/${ventaId}/eliminar-producto/${productId}`);
      setCart(response.data.detalles);
    } catch (err) {
      setError('Error al eliminar producto del carrito');
    }
  };

  // Función para calcular el total de la compra en el backend
  const calculateTotal = async (ventaId) => {
    try {
      const response = await axios.get(`${urlBase}/carrito/${ventaId}/recalcular-total`);
      return response.data;
    } catch (err) {
      setError('Error al calcular el total');
      return 0;
    }
  };

  // Función para registrar la venta
  const registerSale = async (ventaId) => {
    try {
      const response = await axios.post(`${urlBase}/registrar-venta`, { id: ventaId });
      return response.data;
    } catch (error) {
      setError("Error al registrar venta");
    }
  };

  return {
    products,
    cart,
    loading,
    error,
    fetchProducts,
    addToCart,
    removeFromCart,
    updateQuantity,
    calculateTotal,
    registerSale,
  };
};
