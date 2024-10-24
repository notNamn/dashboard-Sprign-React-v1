package com.dashboardProformaVentas.sistemaProformaVentas.controlador;

import com.dashboardProformaVentas.sistemaProformaVentas.dto.LoginRequest;
import com.dashboardProformaVentas.sistemaProformaVentas.dto.RecoverAccountRequest;
import com.dashboardProformaVentas.sistemaProformaVentas.dto.RegisterRequest;
import com.dashboardProformaVentas.sistemaProformaVentas.modelo.User;
import com.dashboardProformaVentas.sistemaProformaVentas.servicio.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            User user = authService.login(request.getEmail(), request.getPassword());
            return  ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try{
            authService.register(request.getName(),request.getEmail(), request.getPassword());
            return  ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/recovery")
    public ResponseEntity<?> recoveryAccount(@RequestBody RecoverAccountRequest request){
        try {
            authService.recoveridAccount(request.getEmail());
            // implemetna que se envie un numero de 4 cifrras aleatorio
            // ejemplo: int numeroRandom = 1234 ;
            return  ResponseEntity.ok("Se han enviado el codigo correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
