import { useState } from 'react';
import axios from 'axios';

export const useCategories = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const urlBase = "http://localhost:8080/dashboard/categorias";

  const fetchCategories = async () => {
    try {
      const response = await axios.get(urlBase);
      setCategories(response.data);
    } catch (error) {
      setError("Error al cargar las categorías.");
    } finally {
      setLoading(false);
    }
  };

  const addCategory = async (categoryName) => {
    try {
      const response = await axios.post(urlBase, { nombreCategoria: categoryName });
      fetchCategories();  // Actualizar lista después de agregar
      return response.data;
    } catch (error) {
      setError("Error al añadir la categoría");
    }
  };

  return {
    categories,
    loading,
    error,
    fetchCategories,
    addCategory,
  };
};
