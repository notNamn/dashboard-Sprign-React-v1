package com.dashboardProformaVentas.sistemaProformaVentas.Dashboard.exepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursosNoEntradoExepcion extends RuntimeException {

    public RecursosNoEntradoExepcion(String mensaje){
        super(mensaje);
    }
}
