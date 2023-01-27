package com.springboot.panecillos.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.panecillos.app.dao.IAdministradorDao;
import com.springboot.panecillos.app.models.domain.Administrador;
@Service
public class AdministradorImplService implements IAdministradorService {
	@Autowired
	private IAdministradorDao administradorDao;
	
	@Override
	public Administrador buscarPorId(Long idadministrador) {
		if(administradorDao.findById(idadministrador).isPresent()) {
			return administradorDao.findById(idadministrador).get();
		}	
		return null;

	}

	@Override
	public List<Administrador> listar() {
		return (List<Administrador>) administradorDao.findAll();
	}

	@Override
	public void eliminar(Long idadministrador) {
		administradorDao.deleteById(idadministrador);
	}

	@Override
	public void guardar(Administrador administrador) {
		administradorDao.save(administrador);	
	}

	@Override
	public Administrador buscarPorCorreoyNombre(String email, String password) {
		Administrador admini=administradorDao.buscarPorCorreo(email, password);
		return admini;
	}

	@Override
	public Administrador buscarPorCorreo(String email) {
		
		return administradorDao.buscarAdministradorPorcorreo(email);
	}

}
