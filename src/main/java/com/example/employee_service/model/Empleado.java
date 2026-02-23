package com.example.employee_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.example.employee_service.enums.PuestoTrabajo;

@Entity
@Table(name = "empleados",
       uniqueConstraints = {
         @UniqueConstraint(name="uk_empleado_correo", columnNames = "correo"),
         @UniqueConstraint(name="uk_empleado_dni",    columnNames = "dni")
       })
public class Empleado {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "usuario_id", nullable = false, unique = true)
  private Long usuarioId;

  @Column(nullable = false, length = 60)
  private String nombre;

  @Column(nullable = false, length = 60)
  private String apellido;

  @Column(nullable = false, length = 120)
  private String correo;

  @Column(nullable = false, length = 12)
  private String dni;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal salario;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PuestoTrabajo puesto;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getUsuarioId() {
	return usuarioId;
}

public void setUsuarioId(Long usuarioId) {
	this.usuarioId = usuarioId;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getApellido() {
	return apellido;
}

public void setApellido(String apellido) {
	this.apellido = apellido;
}

public String getCorreo() {
	return correo;
}

public void setCorreo(String correo) {
	this.correo = correo;
}

public String getDni() {
	return dni;
}

public void setDni(String dni) {
	this.dni = dni;
}

public BigDecimal getSalario() {
	return salario;
}

public void setSalario(BigDecimal salario) {
	this.salario = salario;
}

public PuestoTrabajo getPuesto() {
	return puesto;
}

public void setPuesto(PuestoTrabajo puesto) {
	this.puesto = puesto;
}

public Empleado(Long id, Long usuarioId, String nombre, String apellido, String correo, String dni, BigDecimal salario,
		PuestoTrabajo puesto) {
	super();
	this.id = id;
	this.usuarioId = usuarioId;
	this.nombre = nombre;
	this.apellido = apellido;
	this.correo = correo;
	this.dni = dni;
	this.salario = salario;
	this.puesto = puesto;
}
  
  public Empleado() {}
  
}
