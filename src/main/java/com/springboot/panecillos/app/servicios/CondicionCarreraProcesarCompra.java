package com.springboot.panecillos.app.servicios;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.panecillos.app.dao.IDetalleCompra;
import com.springboot.panecillos.app.models.domain.CarritoCompras;
import com.springboot.panecillos.app.models.domain.Compras;
import com.springboot.panecillos.app.models.domain.DetalleCompras;
import com.springboot.panecillos.app.models.domain.Pago;
import com.springboot.panecillos.app.models.domain.Producto;
import com.springboot.panecillos.app.models.domain.Usuario;
import com.springboot.panecillos.app.service.CorreoService;
import com.springboot.panecillos.app.service.ICarritoService;
import com.springboot.panecillos.app.service.ICategoriaService;
import com.springboot.panecillos.app.service.IComprasService;
import com.springboot.panecillos.app.service.IPagoService;
import com.springboot.panecillos.app.service.IProductoService;
import com.springboot.panecillos.app.service.IUsuarioService;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class CondicionCarreraProcesarCompra implements Runnable{
	
			
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired 
	private ICategoriaService categoriaService; 

	@Autowired
	private ICarritoService carritoService;
	
	@Autowired
	private IDetalleCompra detalleCompraService;
	
	@Autowired 
	private IComprasService comprasService; 
	
	@Autowired 
	private IPagoService pagoService; 
	
	@Autowired
	private IUsuarioService iUsuarioService; 
	
	@Autowired
	private CorreoService enviarCorreoService; 
	
	
	  public static int product = 0;
	  
	  
	  public List<CarritoCompras> carrito(){
			return carritoService.listar();
		}
	  
	    public synchronized void run()
	    {
	        product = 2 * 3;
	        try
	        {
	        	List<CarritoCompras> carritos;
	        	Compras compra=new Compras(); 
	    		Pago pago=new Pago(); 
	    		pago.setMonto(10.0);
	    		pagoService.guardar(pago);
	    		compra.setMonto(10.0);
	    		compra.setPago(pago);
	    		compra.setIdcliente(1L);
	    		
	    		//iUsuarioService.guardar(usuarioPrincipal);
	    		for(CarritoCompras carrito: this.carrito()) {
	    			DetalleCompras detalleCompras=new DetalleCompras(); 
	    			Producto producto=new Producto(); 
	    			producto.setId(carrito.getIdproducto());
	    			detalleCompras.setPrecioCompra(carrito.getPrecioCompra());
	    			detalleCompras.setCantidad(carrito.getCantidad());
	    			detalleCompras.setProducto(producto);
	    			detalleCompraService.save(detalleCompras);
	    			pagoService.guardar(pago);
	    			compra.agregar(detalleCompras);
	    			comprasService.guardar(compra);
	    		}
	    		carritoService.descuento(0.0);
	    		carritoService.listar().clear();
	    		
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
}

class ProcesarCompraCliente implements Runnable
{
    public static int sum = 0;
    public synchronized void run()
    {
        // check if newBarrier is broken or not
        System.out.println("Hay personas queriendo comprar el mismo "
        		+ "producto - barrera rota" + CyclicBarrierAwaitExample1.newBarrier.isBroken());
        sum = 10 + 20;
        try
        {
            CyclicBarrierAwaitExample1.newBarrier.await(3000, TimeUnit.MILLISECONDS);
            
            System.out.println("Número de personas que esperan comprar un producto "+
            "en este punto = " + CyclicBarrierAwaitExample1.newBarrier.getNumberWaiting());
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

class CondicionCarreraParaCompras implements Runnable
{
    public static CyclicBarrier newBarrier = new CyclicBarrier(3);
   
    public static void main(String[] args)
    {
       
        CyclicBarrierAwaitExample1 test = new CyclicBarrierAwaitExample1();
        Thread t1 = new Thread(test);
        t1.start();
    }
    
    @Override
    public synchronized void run()
    { 	
    	int stock=50;
        System.out.println("Compra realizada con exito = "+
        newBarrier.getParties());
        System.out.println("Se ha realizado una compra! = " + (CondicionCarreraProcesarCompra.product + 
        ClienteComprador.sum));
         
   
        CondicionCarreraProcesarCompra comp1 = new CondicionCarreraProcesarCompra();
        ClienteComprador comp2 = new ClienteComprador();
         
        //Hilo comprador y Hilo que recibe la compra
        Thread t1 = new Thread(comp1);
        Thread t2 = new Thread(comp2);
         
        // Inician ambos hilos
        t1.start();
        t2.start();
        try
        {
        	
        	if(stock>0) {
        		 CyclicBarrierAwaitExample1.newBarrier.await();
        	}
        	
        	newBarrier.reset();
           
            
        } 
        catch (InterruptedException | BrokenBarrierException e) 
        {
            e.printStackTrace();
        }
         
        
        System.out.println("Se ha agregado al carrito satisfactoriamente = " + (CondicionCarreraProcesarCompra.product + 
        ClienteComprador.sum));
        // Resetting the newBarrier
        newBarrier.reset();
       
    }
}
