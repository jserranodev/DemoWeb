package com.nttdata.demoweb.service;

import java.util.List;

import com.nttdata.demoweb.repository.EmpleadoRepo;
import com.nttdata.demoweb.repository.entity.Empleado;

public interface EmpleadoService{
	public void registrar(String nombre);
	public List<Empleado> listar();
	public List<Empleado> listarFiltroNombre(String cad);
	public List<Empleado> listarFiltroNombreEs(String nombre);
	public List<Empleado> listarConJPA(Integer pId, String contiene);
	public Empleado inserta(Empleado emp);
	public void modificar(Empleado empleado);
	public void eliminarEmpleado(Integer id);
	public Empleado getById(Integer id);
}
