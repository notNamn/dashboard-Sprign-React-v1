package com.dashboardProformaVentas.sistemaProformaVentas.servicio;

import com.dashboardProformaVentas.sistemaProformaVentas.modelo.User;
import com.dashboardProformaVentas.sistemaProformaVentas.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // autenticar Ususario
    public User login(String email, String password) throws LoginException {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new LoginException("Correo o contraseña invalido");
        }
        return  user;
    }

    // registrar nuevo usuario
    public User register(String name, String email, String password) throws LoginException {
        if (userRepository.findByEmail(email) != null){
            throw new LoginException("El correo ya exixte");
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));

        return  userRepository.save(newUser);
    }

    // recuperar cuenta
    public void recoveridAccount (String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new Exception("No se encontro el correo");
        }
        // aqui hacer que envie algun numero aleatorio
    }

}
