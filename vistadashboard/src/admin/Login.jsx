import { TextField, Button, Typography, Link as MuiLink } from "@mui/material";
import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth"; // El hook ahora maneja las solicitudes directamente

export const Login = () => {
    const navigate = useNavigate();
    const { loginUser, loading, error, isAuthenticated } = useAuth(); // Retornamos isAuthenticated desde el hook
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [success, setSuccess] = useState(false); // Nuevo estado para manejar el éxito

    const handleSubmit = async (e) => {
        e.preventDefault();
        await loginUser(email, password);
    };

    useEffect(() => {
        if (isAuthenticated) {
            setSuccess(true); // Marca el éxito
            console.log(" Inicio de session exitoso ")
            navigate("/dashboard"); // /dashboard
        }
    }, [isAuthenticated, navigate]);

    return (
        <>
            <Typography variant="h4" gutterBottom>Iniciar Sesión</Typography>
            <form onSubmit={handleSubmit} style={{ width: "100%", marginTop: 16 }}>
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="Correo Electrónico"
                    name="email"
                    autoComplete="email"
                    autoFocus
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Contraseña"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                {error && <Typography color="error"> {error} </Typography>}
                {success && <Typography color="success"> Inicio de sesión exitoso, redirigiendo... </Typography>}

                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    style={{ margin: "24px 0 16px" }}
                    disabled={loading}
                >
                    {loading ? 'Cargando ...' : 'Iniciar Sesión'}
                </Button>

                <MuiLink component={Link} to="/register" variant="body2">
                    {"¿No tienes una cuenta? Regístrate"}
                </MuiLink>
                <br />
                <MuiLink component={Link} to="/recover" variant="body2">
                    {"¿Olvidaste tu contraseña?"}
                </MuiLink>
            </form>
        </>
    );
};
