package com.nttdata.demoweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nttdata.demoweb.service.EmpleadoService;

@Controller
public class WebController {

	@Autowired
	EmpleadoService empleadoService;

	@GetMapping("/saludo")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "Mundo") String name,
			Model model) {
		model.addAttribute("name", name);
		empleadoService.registrar(name);
		return "hola"; // web a la que redirige el método
	}

	@GetMapping("/error")
	public String errorPage() {
		return "error";
	}

	@GetMapping("/listarEmpleados")
	@Cacheable(value="empleados")
	public String listarEmp(Model model) {
		model.addAttribute("listaEmp", empleadoService.listar());
		model.addAttribute("listaEmpConE", empleadoService.listarFiltroNombre("e"));
		model.addAttribute("listaEmpNombreExacto", empleadoService.listarFiltroNombreEs("Rocío"));
		model.addAttribute("listaJPA", empleadoService.listarConJPA(2, "%o%"));
		return "listarDeEmpleados";
	}

}
