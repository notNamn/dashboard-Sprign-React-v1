import { TextField, Button, Typography, Link as MuiLink } from '@mui/material';
import { Link, useNavigate } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';

export const Register = () => {
    const { registerUser, loading, error } = useAuth();
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert("Las contraseñas no coinciden");
            return;
        }

        const result = await registerUser(name, email, password);
        if (result) {
            setSuccess(true);
        }
    };

    // Redirigir al login cuando el registro sea exitoso
    useEffect(() => {
        if (success) {
            navigate("/login");
        }
    }, [success, navigate]);

    return (
        <>
            <Typography component="h1" variant="h5">
                Registrar Nuevo Usuario
            </Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="name"
                    label="Nombre Completo"
                    name="name"
                    autoComplete="name"
                    autoFocus
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="Correo Electrónico"
                    name="email"
                    autoComplete="email"
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
                    autoComplete="new-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="confirmPassword"
                    label="Confirmar Contraseña"
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />

                {success && <Typography color="success" style={{ marginTop: '10px' }}>¡Usuario registrado con éxito!</Typography>}
                {error && <Typography color="error" style={{ marginTop: '10px' }}>{error}</Typography>}

                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    style={{ margin: "24px 0 16px" }}
                    disabled={loading}
                >
                    {loading ? 'Registrando...' : 'Registrarse'}
                </Button>

                <MuiLink component={Link} to="/login" variant="body2">
                    {"¿Ya tienes una cuenta? Inicia sesión"}
                </MuiLink>
            </form>
        </>
    );
};
