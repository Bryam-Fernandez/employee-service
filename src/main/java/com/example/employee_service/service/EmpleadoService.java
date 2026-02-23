package com.example.employee_service.service;

import com.example.employee_service.exception.RecursoNoEncontradoException;
import com.example.employee_service.model.Empleado;
import com.example.employee_service.model.EmpleadoForm;
import com.example.employee_service.repository.EmpleadoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepo;

    public EmpleadoService(EmpleadoRepository empleadoRepo) {
        this.empleadoRepo = empleadoRepo;
    }

    // ===== LISTAR =====

    @Transactional(readOnly = true)
    public List<Empleado> listar() {
        return empleadoRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> buscarPorId(Long id) {
        return empleadoRepo.findById(id);
    }

    // ===== CREAR =====
    // Ahora recibe usuarioId desde afuera (por ejemplo desde Feign)
    public Empleado crear(EmpleadoForm form, Long usuarioId) {

        if (empleadoRepo.existsByCorreo(form.getCorreo()))
            throw new IllegalArgumentException("El correo ya existe");

        if (empleadoRepo.existsByDni(form.getDni()))
            throw new IllegalArgumentException("El DNI ya existe");

        Empleado emp = new Empleado();
        emp.setUsuarioId(usuarioId);
        emp.setNombre(form.getNombre());
        emp.setApellido(form.getApellido());
        emp.setCorreo(form.getCorreo());
        emp.setDni(form.getDni());
        emp.setSalario(form.getSalario());
        emp.setPuesto(form.getPuesto());

        return empleadoRepo.save(emp);
    }

    // ===== ACTUALIZAR =====

    public Empleado actualizar(Long id, EmpleadoForm form) {

        Empleado emp = empleadoRepo.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Empleado no encontrado"));

        if (form.getCorreo() != null
                && !form.getCorreo().equals(emp.getCorreo())
                && empleadoRepo.existsByCorreo(form.getCorreo())) {
            throw new IllegalArgumentException("El correo ya existe");
        }

        if (form.getDni() != null
                && !form.getDni().equals(emp.getDni())
                && empleadoRepo.existsByDni(form.getDni())) {
            throw new IllegalArgumentException("El DNI ya existe");
        }

        if (form.getNombre() != null) emp.setNombre(form.getNombre());
        if (form.getApellido() != null) emp.setApellido(form.getApellido());
        if (form.getCorreo() != null) emp.setCorreo(form.getCorreo());
        if (form.getDni() != null) emp.setDni(form.getDni());
        if (form.getSalario() != null) emp.setSalario(form.getSalario());
        if (form.getPuesto() != null) emp.setPuesto(form.getPuesto());

        return empleadoRepo.save(emp);
    }

    // ===== ELIMINAR =====

    public void eliminar(Long id) {

        Empleado emp = empleadoRepo.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Empleado no encontrado"));

        empleadoRepo.delete(emp);


    }

    // ===== PAGINACIÓN =====

    @Transactional(readOnly = true)
    public Page<Empleado> listarPaginado(int pagina, int tamanio) {
        return empleadoRepo.findAll(PageRequest.of(pagina, tamanio));
    }

    @Transactional(readOnly = true)
    public Page<Empleado> buscarPorNombreApellidoPaginado(
            String q, int pagina, int tamanio) {

        if (q == null || q.isBlank()) {
            return listarPaginado(pagina, tamanio);
        }

        return empleadoRepo
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
                        q, q, PageRequest.of(pagina, tamanio));
    }
}