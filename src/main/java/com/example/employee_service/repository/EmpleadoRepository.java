package com.example.employee_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee_service.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
  boolean existsByCorreo(String correo);
  boolean existsByDni(String dni);
  
  Page<Empleado> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido, Pageable pageable);


  Page<Empleado> findAll(Pageable pageable);

}