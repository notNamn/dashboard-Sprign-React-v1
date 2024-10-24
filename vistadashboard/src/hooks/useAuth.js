import { useState } from "react";
import axios from "axios";

export const useAuth = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    const urlBase = 'http://localhost:8080/api/auth';

    const loginUser = async (email, password) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.post(`${urlBase}/login`, { email, password });
            if (response.status === 200) {
                setIsAuthenticated(true);  
            }
        } catch (error) {
            if (error.response && error.response.data) {
                setError(error.response.data.message || "Error al iniciar sesión.");
            } else {
                setError("Error al conectar con el servidor.");
            }
        } finally {
            setLoading(false);
        }
    };

    const registerUser = async (name, email, password) =>{
        setLoading(true);
        setError(null);
        try {
            const response = await axios.post(`${urlBase}/register`, { name, email, password });
            if(response.status === 201){
                setIsAuthenticated(true);
            }
        } catch (error) {
            setError(error.response?.data?.message || "Error al registrar.");
        }finally{
            setLoading(false);
        }
    };

    const recoverAccount = async (email, code, newPassword) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.post(`${urlBase}/recovery`, { email, code, newPassword });
            return response.data; // Respuesta de éxito
        } catch (error) {
            setError(error.response?.data?.message || "Error en la recuperación de cuenta.");
        } finally {
            setLoading(false);
        }
    };

    

    return {
        loading,
        error,
        loginUser,
        registerUser,
        recoverAccount,
        isAuthenticated,
    };
};
