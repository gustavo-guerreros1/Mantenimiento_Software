package com.springboot.panecillos.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.panecillos.app.dao.IProductoDao;
import com.springboot.panecillos.app.models.domain.Producto;

@Service
public class ProductoServiceImpl implements IProductoService{

	@Autowired 
	private IProductoDao productodao;
	
	@Override
	public List<Producto> findAll() {
		return (List<Producto>) productodao.findAll();
	}

	@Override
	public Producto buscarPorId(Long id) {
		
	Optional<Producto> optional= productodao.findById(id);
	if(optional.isPresent()) {
		return optional.get();
	}
		return null;
	}

	@Override
	public List<Producto> listarProductosBusqueda(String nombre) {
		
		return productodao.listarProductosBusqueda(nombre);
	}

	@Override
	public void eliminar(Long id) {
		Producto producto=buscarPorId(id);
		productodao.delete(producto);
		
	}

	@Override
	public void guardar(Producto producto) {
		productodao.save(producto);
	}

	@Override
	public List<Producto> listarProductosPrecio(Double precio) {
		return productodao.listarProductosPrecio(precio);
	}

	@Override
	public List<Producto> listarProductosPrecioMenor15(Double precio) {
		return productodao.listarProductosPrecioMenor15(precio);
	}

	@Override
	public Page<Producto> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return productodao.findAll(pageable);
	}

}
