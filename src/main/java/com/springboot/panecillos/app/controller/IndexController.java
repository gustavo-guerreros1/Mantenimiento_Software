package com.springboot.panecillos.app.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.panecillos.app.dao.IDetalleCompra;
import com.springboot.panecillos.app.models.domain.Administrador;
import com.springboot.panecillos.app.models.domain.CarritoCompras;
import com.springboot.panecillos.app.models.domain.Categoria;

import com.springboot.panecillos.app.models.domain.Compras;
import com.springboot.panecillos.app.models.domain.DetalleCompras;
import com.springboot.panecillos.app.models.domain.Pago;
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




@SessionAttributes("producto")
@Controller
public class IndexController {
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
	
	//@Autowired
	//private PasswordEncoder passwordEncoder;
	
	int item=0;
	@GetMapping({"/index", "/", ""})
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
		System.out.println("GAAAAAAAAAAAAAAAAAAAA: "+valor);
		if(valor==5 || valor==10 || valor==2) {
			return "gaaa";
		}
		Producto producto=productoService.buscarPorId(id);
		model.addAttribute("producto", producto);
		return "detalleproducto";
	}
	
	@PostMapping("/agregarcarrito")
	public String agregarCarrito(@RequestParam Long id, @RequestParam Integer cantidad) {
		item=item+1;
		Producto  producto=productoService.buscarPorId(id);
		CarritoCompras carrito=new CarritoCompras(); 
		carrito.setIdproducto(producto.getId());
		carrito.setCantidad(cantidad);
		carrito.setItem(item);
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
		if(cupon.equals("anitaux2022")) {
			carritoService.descuento(20.00);
		}
		return "redirect:/carrito";
	}
	

	@GetMapping("/signUp")
	public String iniciarSesion() {
		return "/login";
	}
	
	@PostMapping("/signUp")
	public String procesarFormulario(Usuario usuario, RedirectAttributes flash) {
		String pwdPlano=usuario.getPassword();
		//String pwdEncriptado=passwordEncoder.encode(pwdPlano);
		//usuario.setPassword(pwdEncriptado);
		//System.out.println("=================================================================================== ");
		if(usuario.getEmail().isEmpty()
				|| usuario.getPassword().isEmpty() 
				|| !usuario.getEmail().contains("@")
				|| usuario.getNombre().isEmpty()
				|| usuario.getApellido().isEmpty()) {
			flash.addFlashAttribute("error2", "Faltan datos");
			return "redirect:/index";
		}
		iUsuarioService.guardar(usuario);
		enviarCorreoService.sendEmail(usuario.getEmail());
		flash.addFlashAttribute("success", "usuario creado correctamente");
		
		return "redirect:/index"; 
	}
	
	@PostMapping("/iniciar")
	public String iniciar(@RequestParam String email, @RequestParam String password, RedirectAttributes flash) {
		
		
		
		if(email.isEmpty() || password.isEmpty()) {
			flash.addFlashAttribute("error", "No puede estar vacios los campos");
			return "redirect:/login";
		}
		
		Usuario usuario= iUsuarioService.buscarPorCorreoyNombre(email, password);
		
		
		this.usuarioPrincipal=usuario;
		return "redirect:/index";
	}
	
	@GetMapping("/cerrarSesion")
	public String cerrarSession() {
		this.usuarioPrincipal=null;
		carritoService.descuento(0.0);
		return "redirect:/index";
	}
	
	@GetMapping("/miscompras")
	public String misCompras(Model model) {
		if(this.usuarioPrincipal==null) {
			return "redirect:/login";
		}
		List<Compras> compras=comprasService.comprasDelUsuario(usuarioPrincipal.getId());
		model.addAttribute("compras", compras);
		return "miscompras";
	}
	
	@GetMapping("/detalleMisCompras/{id}")
	public String detalleMisCompras(@PathVariable Long id, Model model) {
		List<DetalleCompras> detalleCompras=detalleCompraService.detallesCompras(id);
		model.addAttribute("detalle", detalleCompras);
		return "detallemiscompras";
	}
	
	@GetMapping("/filtrarCategoria/{id}")
	public String filtrarCategoria(@PathVariable Long id, Model model) {
		Categoria categoria=categoriaService.findOne(id);
		model.addAttribute("productos", categoria.getProducto());
		return "filtrar"; 
	}
	
	@GetMapping("/filtrarPrecios/{id}")
	public String filtrarPrecios(@PathVariable Long id, Model model) {
		List<Producto> productos=new ArrayList<Producto>();
		productoService.listarProductosPrecio(15.00).stream().forEach(n-> System.out.println(n.getNombre()));
		if(id==1) {
			productos=productoService.listarProductosPrecio(15.00);
		}
		
		if(id==2) {
			productos=productoService.listarProductosPrecio(30.00);
		}
		
		if(id==3) {
			productos=productoService.listarProductosPrecioMenor15(15.00);
		}
		
		model.addAttribute("productos", productos);
		return "filtrar"; 
	}
	
	
	@GetMapping("/buscar")
	public String buscar(@RequestParam String producto,Model model) {
		//DEBEMOS TRABAJAR CON MINUSCULAS PARA HACER REALIZAR LA BUSQUEDA HOMOGENEAS 
		producto=producto.toLowerCase();
		List<Producto> productos=productoService.listarProductosBusqueda(producto);
		model.addAttribute("productos", productos);
		return "filtrar"; 
	}
	
	@GetMapping("/compra")
	public String compra(Model model) {
		model.addAttribute("tarjetaCredito", this.tarjetaCredito);
		return "compra";
	}
	
	
	@GetMapping("/procesarcompra")
	public String procesarcompra() {
		Compras compra=new Compras(); 
		Pago pago=new Pago(); 
		pago.setMonto(totalPagar());
		pagoService.guardar(pago);
		compra.setMonto(totalPagar());
		compra.setPago(pago);
		compra.setIdcliente(usuarioPrincipal.getId());
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
		return "redirect:/miscompras";
	}
	
	@PostMapping("validarTarjeta")
	public String pago(TarjetaCredito tarjetaCredito, RedirectAttributes flash, Model model) {
		System.out.println(tarjetaCredito.getNumeroTarjeta()+ " "+
					tarjetaCredito.getMm()+" "+tarjetaCredito.getCvc());
		if(tarjetaCredito.getNumeroTarjeta().length()!=16
				|| tarjetaCredito.getCvc().length()!=3 
				|| tarjetaCredito.getNumeroTarjeta().isEmpty()
				|| tarjetaCredito.getCodigoPostal().isEmpty()) {
			model.addAttribute("mensaje", "Tarjeta invalida");
			return "compra";	
		}
		
		model.addAttribute("exito", "exito");

		return "compra";
	}
	
	@GetMapping("validarTarjeta")
	public String validarTarjeta() {
		return "redirect:/compra";
	}

	@ModelAttribute("totalPagar")
	public Double totalPagar() {
		return Math.round(carritoService.calcularTotal()*100.0)/100.0 ;
	}
	
	@ModelAttribute("totalPagarSinDescuento")
	public Double totalPagarSinDescuento() {
		return Math.round(carritoService.calcularTotalSinDescuento()*100.0)/100.0;  
	}
	
	@ModelAttribute("listarCarrito")
	public List<CarritoCompras> carrito(){
		return carritoService.listar();
	}
	
	@ModelAttribute("usuarioPrincipal")
	public Usuario usuario() {
		return this.usuarioPrincipal;
	}
	
	@GetMapping("/admin/login")
	public String loginAdmin() {
		
		return "admilogin";
	}
	
	@GetMapping("/admin/password")
	public String recuperarPassword() {
		return "adminpassword";
	}
	
	@PostMapping("/admin/login")
	public String postAdminLogin(String correo, String password, RedirectAttributes flash) {
		System.out.println("Correo: "+correo+" Password: "+password);
		if(correo.trim().isEmpty()|| password.trim().isEmpty()) {
			flash.addFlashAttribute("error", "Los campos no pueden estar vacios");
			return "redirect:/admin/login";
		}
		
		Administrador admi= administradorService.buscarPorCorreoyNombre(correo, password);
		
		if(admi==null) {
			flash.addFlashAttribute("error", "El usuario y la contraseña no coinciden");
			return "redirect:/admin/login";
		}
		
		return "redirect:/admin/index";
	}

	
}
