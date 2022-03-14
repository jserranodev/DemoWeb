package com.nttdata.demoweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nttdata.demoweb.repository.UsuarioRepoJPA;
import com.nttdata.demoweb.repository.entity.Usuario;
import com.nttdata.demoweb.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	@Autowired
	UsuarioRepoJPA usuarioDAO;

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return usuarioDAO.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioDAO.findById(username).get(); // Devuelve un OPTIONAL.class
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return buscarPorUsername(username);
	}

}
