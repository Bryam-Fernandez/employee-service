package com.example.employee_service.controller;

import com.example.employee_service.model.Empleado;
import com.example.employee_service.model.EmpleadoForm;
import com.example.employee_service.service.EmpleadoService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // ===== LISTAR TODOS =====
    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        return ResponseEntity.ok(empleadoService.listar());
    }

    // ===== LISTAR PAGINADO =====
    @GetMapping("/paginado")
    public ResponseEntity<Page<Empleado>> listarPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        return ResponseEntity.ok(
                empleadoService.listarPaginado(pagina, tamanio)
        );
    }

    // ===== BUSCAR POR ID =====
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Long id) {

        Empleado emp = empleadoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        return ResponseEntity.ok(emp);
    }

    // ===== BUSCAR POR NOMBRE O APELLIDO =====
    @GetMapping("/buscar")
    public ResponseEntity<Page<Empleado>> buscar(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        return ResponseEntity.ok(
                empleadoService.buscarPorNombreApellidoPaginado(q, pagina, tamanio)
        );
    }

    // ===== CREAR =====
    // Por ahora recibimos usuarioId como parámetro
    @PostMapping
    public ResponseEntity<Empleado> crear(
            @RequestBody EmpleadoForm form,
            @RequestParam Long usuarioId) {

        Empleado emp = empleadoService.crear(form, usuarioId);

        return ResponseEntity
                .created(URI.create("/api/empleados/" + emp.getId()))
                .body(emp);
    }

    // ===== ACTUALIZAR =====
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(
            @PathVariable Long id,
            @RequestBody EmpleadoForm form) {

        return ResponseEntity.ok(
                empleadoService.actualizar(id, form)
        );
    }

    // ===== ELIMINAR =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}