package com.springboot.panecillos.app.service;

import java.util.List;

import com.springboot.panecillos.app.models.domain.Administrador;
import com.springboot.panecillos.app.models.domain.Usuario;

public interface IAdministradorService {
	public Administrador buscarPorId(Long idadministrador);
	public List<Administrador> listar();
	public void eliminar(Long idadministrador);
	public void guardar(Administrador administrador);
	public Administrador buscarPorCorreoyNombre(String email, String password);
	public Administrador buscarPorCorreo(String email);
}
