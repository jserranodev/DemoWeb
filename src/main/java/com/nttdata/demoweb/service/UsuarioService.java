package com.nttdata.demoweb.service;

import java.util.List;

import com.nttdata.demoweb.repository.entity.Usuario;

public interface UsuarioService {
	public List<Usuario> listar();

	public Usuario buscarPorUsername(String username);
}
