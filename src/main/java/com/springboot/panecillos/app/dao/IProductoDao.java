package com.springboot.panecillos.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.panecillos.app.models.domain.Producto;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Long>{
	//Se homogeniza la query 
	@Query(value = "select * from productos where LOWER(nombre) like %?1%", nativeQuery = true)
	public List<Producto> listarProductosBusqueda(String nombre);
	
	@Query(value = "select * from productos where precio > ?1 order by precio", nativeQuery = true)
	public List<Producto> listarProductosPrecio(Double precio);
	
	@Query(value = "select * from productos where precio < ?1 order by precio", nativeQuery = true)
	public List<Producto> listarProductosPrecioMenor15(Double precio);
}
