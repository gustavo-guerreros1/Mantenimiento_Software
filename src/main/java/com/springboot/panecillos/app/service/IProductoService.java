package com.springboot.panecillos.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.panecillos.app.models.domain.Producto;

public interface IProductoService  {
	public List<Producto> findAll();
	public Producto buscarPorId(Long id);
	public List<Producto> listarProductosBusqueda(String nombre);
	public List<Producto> listarProductosPrecio(Double precio);
	public List<Producto> listarProductosPrecioMenor15(Double precio);
	public Page<Producto> findAll(Pageable pageable);
	public void eliminar(Long id);
	public void guardar(Producto producto);
}
