package com.nttdata.demoweb.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.demoweb.repository.entity.Empleado;
import com.nttdata.demoweb.service.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoRestController {

	@Autowired
	EmpleadoService empleadoService;

	@GetMapping
	@Cacheable(value = "empleados")
	public List<Empleado> listarEmpleados() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		return empleadoService.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empleado> devuelveEmpleado(@PathVariable("id") Integer id) {
		Empleado emp = empleadoService.getById(id);

		if (emp == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(emp, HttpStatus.OK);
	}

//	@PostMapping
//	@CacheEvict(value = "empleados", allEntries = true) // Expira cache con nombre "empleados"
//	public void insertarEmpleado(@RequestBody Empleado emp) {
//		emp.setId(null);
//		empleadoService.inserta(emp);
//	}

//	@PostMapping
//	@CacheEvict(value = "empleados", allEntries = true) // Expira cache con nombre "empleados"
//	public ResponseEntity<List<Empleado>> insertarEmpleado(@RequestBody Empleado emp) {
//		try {
//			emp.setId(null);
//			empleadoService.inserta(emp);
//			return new ResponseEntity<>(empleadoService.listar(), HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@CacheEvict(value = "empleados", allEntries = true)
	@PostMapping
	public ResponseEntity<Empleado> insertarEmpleado_v3(@RequestBody Empleado empleado) {
		try {
			HttpHeaders headers = new HttpHeaders();
			if (empleado.getId() != null) {
				headers.set("Message", "Para dar de alta un nuevo empleado, el ID debe llegar vacío");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			} else if (empleado.getNombre() == null || empleado.getNombre().equals("")
					|| empleado.getApellidos() == null || empleado.getApellidos().equals("")) {
				headers.set("Message", "Ni NOMBRE ni APELLIDOS pueden ser nulos");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}

			Empleado emp = empleadoService.inserta(empleado);
			URI newPath = new URI("/api/empleados/" + emp.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Empleado insertado correctamente con id: " + emp.getId());

			return new ResponseEntity<>(emp, headers, HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	@CacheEvict(value = "empleados", allEntries = true)
	public void modificarEmpleado(@RequestBody Empleado empleado) {
		empleadoService.modificar(empleado);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "empleados", allEntries = true)
	public void eliminarEmplado(@PathVariable("id") Integer id) {
		empleadoService.eliminarEmpleado(id);
	}

}
