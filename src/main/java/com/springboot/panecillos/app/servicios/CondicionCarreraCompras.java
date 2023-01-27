package com.springboot.panecillos.app.servicios;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.panecillos.app.models.domain.CarritoCompras;
import com.springboot.panecillos.app.models.domain.Producto;
import com.springboot.panecillos.app.service.ICarritoService;
import com.springboot.panecillos.app.service.ICategoriaService;
import com.springboot.panecillos.app.service.IProductoService;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class CondicionCarreraCompras implements Runnable{
	
			
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired 
	private ICategoriaService categoriaService; 

	@Autowired
	private ICarritoService carritoService;
	
	
	long id=10;
	int stock=50;
	int cantidad=stock;
	
	
	  public static int product = 0;
	    public synchronized void run()
	    {
	        product = 2 * 3;
	        try
	        {
	        	Producto  producto=productoService.buscarPorId(id);
	    		CarritoCompras carrito=new CarritoCompras(); 
	    		carrito.setIdproducto(producto.getId());
	    		carrito.setCantidad(cantidad);
	    		carrito.setNombre(producto.getNombre());
	    		carrito.setPrecioCompra(producto.getPrecio());
	    		carrito.setSubTotal(Math.round(cantidad*producto.getPrecio()*100.0)/100.0);
	    		carrito.setFoto(producto.getFoto());
	    		carritoService.guardar(carrito);
	    		
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
}

class ClienteComprador implements Runnable
{
    public static int sum = 0;
    public synchronized void run()
    {
        // check if newBarrier is broken or not
        System.out.println("Hay personas queriendo comprar el mismo "
        		+ "producto - barrera rota" + CondicionCarreraParaCompras.newBarrier.isBroken());
        sum = 10 + 20;
        try
        {
            CondicionCarreraParaCompras.newBarrier.await(3000, TimeUnit.MILLISECONDS);
            
            System.out.println("Número de personas que esperan comprar un producto "+
            "en este punto = " + CondicionCarreraParaCompras.newBarrier.getNumberWaiting());
        } catch (InterruptedException | BrokenBarrierException e) 
        {
            e.printStackTrace();
        } 
        catch (TimeoutException e) 
        {
            e.printStackTrace();
        }
    }
}

class CyclicBarrierAwaitExample1 implements Runnable
{
    public static CyclicBarrier newBarrier = new CyclicBarrier(3);
   
    public static void main(String[] args)
    {
       
        CondicionCarreraParaCompras test = new CondicionCarreraParaCompras();
        Thread t1 = new Thread(test);
        t1.start();
    }
    
    @Override
    public synchronized void  run()
    { 	
    	int stock=50;
        System.out.println("Compra realizada con exito = "+
        newBarrier.getParties());
        System.out.println("Se ha realizado una compra! = " + (CondicionCarreraCompras.product + 
        ProcesarCompraCliente.sum));
         
   
        CondicionCarreraCompras comp1 = new CondicionCarreraCompras();
        ProcesarCompraCliente comp2 = new ProcesarCompraCliente();
         
        //Hilo comprador y Hilo que recibe la compra
        Thread t1 = new Thread(comp1);
        Thread t2 = new Thread(comp2);
         
        // Inician ambos hilos
        t1.start();
        t2.start();
        try
        {
        	
        	if(stock>0) {
        		 CondicionCarreraParaCompras.newBarrier.await();
        	}
        	
        	newBarrier.reset();
           
            
        } 
        catch (InterruptedException | BrokenBarrierException e) 
        {
            e.printStackTrace();
        }
         
        
        System.out.println("Se ha agregado al carrito satisfactoriamente = " + (CondicionCarreraCompras.product + 
        ProcesarCompraCliente.sum));
        // Resetting the newBarrier
        newBarrier.reset();
       
    }
}
