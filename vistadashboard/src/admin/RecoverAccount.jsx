import React from 'react'
import { TextField, Button, Typography, Link as MuiLink } from '@mui/material';
import { Link } from "react-router-dom";


export const RecoverAccount = () => {
  return (
    <>
    <Typography component="h1" variant="h5">
      Recuperar Cuenta
    </Typography>
    <form  style={{ width: '100%', marginTop: 1 }}>
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
      />
      <Button
        type="submit"
        fullWidth
        variant="contained"
        color="primary"
        style={{ margin: '24px 0 16px' }}
      >
        Enviar Codigo de Recuperacion 
      </Button>
      
      <MuiLink component={Link} to="/login" variant="body2">
          {"Volver al inicio de sesión"}
      </MuiLink>

    </form>
  </>
  )
}
