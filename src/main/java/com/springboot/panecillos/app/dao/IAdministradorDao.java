package com.springboot.panecillos.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.panecillos.app.models.domain.Administrador;
import com.springboot.panecillos.app.models.domain.Usuario;

public interface IAdministradorDao extends CrudRepository<Administrador, Long> {
	@Query(value = "select * from administrador where correo=?1 and password=?2", nativeQuery = true)
	 Administrador buscarPorCorreo(String correo, String password);
	
	@Query(value = "select * from administrador where correo=?1", nativeQuery = true)
	 Administrador buscarAdministradorPorcorreo(String correo);

}
