package com.springboot.panecillos.app.servicios;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.panecillos.app.dao.IDetalleCompra;
import com.springboot.panecillos.app.models.domain.CarritoCompras;
import com.springboot.panecillos.app.models.domain.Categoria;
import com.springboot.panecillos.app.models.domain.Producto;
import com.springboot.panecillos.app.models.domain.TarjetaCredito;
import com.springboot.panecillos.app.models.domain.Usuario;
import com.springboot.panecillos.app.service.CorreoService;
import com.springboot.panecillos.app.service.IAdministradorService;
import com.springboot.panecillos.app.service.ICarritoService;
import com.springboot.panecillos.app.service.ICategoriaService;
import com.springboot.panecillos.app.service.IComprasService;
import com.springboot.panecillos.app.service.IPagoService;
import com.springboot.panecillos.app.service.IProductoService;
import com.springboot.panecillos.app.service.IUsuarioService;

public class HiloServidor extends Thread {

	private Usuario usuarioPrincipal=null;
	
	private TarjetaCredito tarjetaCredito=new TarjetaCredito();
	
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
	
	@Autowired
	private IAdministradorService administradorService;
	
	
	public String index(Model model) {
		Categoria categoria=categoriaService.findOne(1L);
		Categoria categoriaPasteles=categoriaService.findOne(2L);
		model.addAttribute("categoriaPasteles", categoriaPasteles);
		model.addAttribute("categoriaPan", categoria);

		return "index";
	}
	
	@GetMapping("/confirmarCorreo")
	public String confimarCorreo(Model model) {
		Categoria categoria=categoriaService.findOne(1L);
		Categoria categoriaPasteles=categoriaService.findOne(2L);
		model.addAttribute("categoriaPasteles", categoriaPasteles);
		model.addAttribute("categoriaPan", categoria);
		
		return "index2";
	}
	
	@GetMapping("/productos")
	public String listaProductos(Model model) {
		List<Producto> productos=productoService.findAll();
		model.addAttribute("productos", productos);
		return "productos";
	}
	
	
	@GetMapping("/detalle/{id}")
	public String categorias(@PathVariable Long id, Model model) {
		Random random=new Random();
		int valor=random.nextInt(10);
		//System.out.println("GAAAAAAAAAAAAAAAAAAAA: "+valor);
		if(valor==5 || valor==10 || valor==2) {
			return "gaaa";
		}
		Producto producto=productoService.buscarPorId(id);
		model.addAttribute("producto", producto);
		return "detalleproducto";
	}
	
	@PostMapping("/agregarcarrito")
	public String agregarCarrito(@RequestParam Long id, @RequestParam Integer cantidad) {
		
		Producto  producto=productoService.buscarPorId(id);
		CarritoCompras carrito=new CarritoCompras(); 
		carrito.setIdproducto(producto.getId());
		carrito.setCantidad(cantidad);
		carrito.setNombre(producto.getNombre());
		carrito.setPrecioCompra(producto.getPrecio());
		carrito.setSubTotal(Math.round(cantidad*producto.getPrecio()*100.0)/100.0);
		carrito.setFoto(producto.getFoto());
		carritoService.guardar(carrito);
		return "redirect:/index"; 
	}

	@GetMapping("/carrito")
	public String carro(Model model, HttpSession session) {
		return "carrito";
	}
	
	@GetMapping("/delete/{idproducto}")
	public String eliminar(@PathVariable Long idproducto) {
		carritoService.eliminar(idproducto);
		return "redirect:/carrito";
	}
	
	@GetMapping("/descuento")
	public String descuento(@RequestParam String cupon, Model model) {
		System.out.println(cupon);
		if(cupon.equals("gestion")) {
			carritoService.descuento(30.00);
		}
		return "redirect:/carrito";
	}
	

	@GetMapping("/signUp")
	public String iniciarSesion() {
		return "/login";
	}
	
	
}
