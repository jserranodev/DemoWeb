package com.nttdata.demoweb.repository;

import java.util.List;

import com.nttdata.demoweb.repository.entity.Empleado;

public interface EmpleadoRepo {
	public void registrar (String nombre);
	public List<Empleado> listarCuyoNombreContiene(String text_nombre);
}
