import axios from "axios";
import { useEffect, useState } from "react";

export const useProducts = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const urlBase = "http://localhost:8080/ventas";

  // obtener todos los productos
  const fetchProducts = async (query) => {
    setLoading(true);
    try {
      // Cambiar a la URL correcta según tu controlador de Spring Boot
      const response = await axios.get(`${urlBase}/productos?search=${query}`);
      if (Array.isArray(response.data)) {
        setProducts(response.data);
      } else {
        setProducts([]);
      }
    } catch (err) {
      setError('Error al buscar productos');
    } finally {
      setLoading(false);
    }
  };
  

  // agregar un nuevo producto
  const addProduct = async (product) => {
    try {
      await axios.post(`${urlBase}/productos`, product);
      fetchProducts();
    } catch (error) {
      setError("Error al añadir el producto");
    }
  };

  // actualizar
  const updateProduct = async (id, product) => {
    try {
      await axios.put(`${urlBase}/productos/${id}`, product);
      fetchProducts();
    } catch (error) {
      setError("Error al actualizar el producto");
    }
  };

  // eliminar
  const deleteProduct = async (id) => {
    try {
      await axios.delete(`${urlBase}/productos/${id}`);
      fetchProducts();
    } catch (error) {
      setError("Error al eliminar producto");
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return {
    products,
    loading,
    error,
    fetchProducts,
    addProduct,
    updateProduct,
    deleteProduct,
  };
};
