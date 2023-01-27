package com.springboot.panecillos.app.models.domain;

import java.util.Date;

public class ClienteReporte {
	private String nombre;
	private Double precio; 
	private Integer cantidad; 
	private Double total;
	private Date fecha;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		return "ClienteReporte [nombre=" + nombre + ", precio=" + precio + ", cantidad=" + cantidad + ", total=" + total
				+ ", fecha=" + fecha + "]";
	}
	
	
	
	

	
}
