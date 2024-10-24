package com.dashboardProformaVentas.sistemaProformaVentas.repositorio;

import com.dashboardProformaVentas.sistemaProformaVentas.modelo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    // buscar un usuario por correo
    User findByEmail(String email);
}
