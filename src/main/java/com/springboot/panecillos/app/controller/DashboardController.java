package com.springboot.panecillos.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.springboot.panecillos.app.dao.IDetalleCompra;
import com.springboot.panecillos.app.models.domain.Administrador;
import com.springboot.panecillos.app.models.domain.Compras;
import com.springboot.panecillos.app.models.domain.DetalleCompras;
import com.springboot.panecillos.app.models.domain.Producto;
import com.springboot.panecillos.app.models.domain.Usuario;
import com.springboot.panecillos.app.paginator.PageRender;
import com.springboot.panecillos.app.service.CorreoService;
import com.springboot.panecillos.app.service.IAdministradorService;
import com.springboot.panecillos.app.service.IComprasService;
import com.springboot.panecillos.app.service.IProductoService;
import com.springboot.panecillos.app.service.IUsuarioService;

@Controller
@RequestMapping("/admin")
public class DashboardController {
	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Autowired
	private IComprasService icomprasService;
	
	@Autowired 
	private IDetalleCompra iDetalleCompra;
	
	@Autowired
	private IProductoService iproductoService;
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAdministradorService administradorService;
	
	@Autowired
	private CorreoService correoService;
	
	@GetMapping("/index")
	public String index() {
		return "dashboard/index";
	}
	
	@GetMapping("/clientes")
	public String clientes() {
		return "dashboard/clientecompras";
	}
	
	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		List<Administrador> lista=new ArrayList<Administrador>(); 
		lista=administradorService.listar();
		model.addAttribute("lista", lista);
;		return "dashboard/usuarios";
	}
	
	@GetMapping("/clientes/{id}")
	public String misCompras(@PathVariable Long id ,Model model) {
		List<Compras> compras=icomprasService.comprasDelUsuario(id);
		model.addAttribute("compras", compras);
		return "dashboard/miscompras";
	}
	
	@GetMapping("/detalleMisCompras/{id}")
	public String detalleMisCompras(@PathVariable Long id, Model model) {
		List<DetalleCompras> detalleCompras=iDetalleCompra.detallesCompras(id);
		model.addAttribute("detalle", detalleCompras);
		return "dashboard/detallecompras";
	}
	
	@GetMapping("/productos")
	public String productos(Model model) {
		List<Producto> productos= iproductoService.findAll();
		model.addAttribute("productos", productos);
		return "dashboard/productos";
	}
	
	@GetMapping("/productosPage")
	public String productosPaginador(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest=PageRequest.of(page, 5);
		Page<Producto> productos=iproductoService.findAll(pageRequest);
		PageRender<Producto> pageRender=new PageRender<>("/admin/productosPage", productos);
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "dashboard/productos";
	}
	
	@GetMapping("/deleteProducto/{id}")
	public String productosDelete(@PathVariable Long id,Model model) {
		iproductoService.eliminar(id);
		return "redirect:/admin/productosPage";
	}
	
	@GetMapping("/crear")
	public String productosCrear(Model model) {
		Producto producto=new Producto();
		model.addAttribute("producto", producto);
		return "dashboard/crearProducto";
	}
	
	@PostMapping("/crear")
	public String procesarProducto(Producto producto , Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash) {
		if(!foto.isEmpty()) {
			//String rootPath="C://Temp//uploads";
			String uniqueFilename=UUID.randomUUID().toString()+"_"+foto.getOriginalFilename();
			Path rootPath=Paths.get("uploads").resolve(foto.getOriginalFilename());
			Path rootAbsolutPah=rootPath.toAbsolutePath();
			log.info("rootPath: "+rootPath);
			log.info("rootAbsolutPath: "+rootAbsolutPah);
			try {
				/*
				byte[] bytes=foto.getBytes();
				Path rutaCompleta=Paths.get(rootPath+"/"+foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				*/
				Files.copy(foto.getInputStream(), rootAbsolutPah);
				
				producto.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		iproductoService.guardar(producto);
		flash.addFlashAttribute("success", "Producto creado correctamente");
		return "redirect:/admin/productosPage";
	}
	
	@GetMapping("/crearAdministrador")
	public String crearAdmi(Model model) {
		Administrador admi=new Administrador(); 
		model.addAttribute("administrador", admi);
		return "dashboard/crearAdministrador";
	}
	
	@PostMapping("/crearAdministrador")
	public String crearAdministrador(Administrador administrador, RedirectAttributes flash, Model model) {
		System.out.println(administrador.getPassword());
		if(administrador.getNombre().trim().isEmpty() || administrador.getNombre().trim().isEmpty() || 
				administrador.getApellido().trim().isEmpty() ||
				administrador.getCorreo().trim().isEmpty() 
				) {
			flash.addFlashAttribute("error", "Los campos no pueden ser vacios");
			return "redirect:/admin/crearAdministrador";
		}
		
		if(!administrador.getCorreo().contains("@")) {
			flash.addFlashAttribute("error", "ingrese un correo valido");
			return "redirect:/admin/crearAdministrador";
		}
		administradorService.guardar(administrador);
		flash.addFlashAttribute("success", "Administrador creado con exito");
		return "redirect:/admin/usuarios";
	}
	
	@GetMapping("/deleteAdministrador/{id}")
	public String administradorDelete(@PathVariable Long id,Model model) {
		administradorService.eliminar(id);
		return "redirect:/admin/usuarios";
	}
	
	
	@GetMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id,Model model) {
		Producto producto=iproductoService.buscarPorId(id);
		model.addAttribute("producto", producto);
		return "dashboard/crearProducto";
	}
	
	@GetMapping("/editarAdministrador/{id}")
	public String editarAdministrador(@PathVariable Long id,Model model) {
		Administrador admin=administradorService.buscarPorId(id);
		model.addAttribute("administrador", admin);
		model.addAttribute("editar", "editar");
		return "dashboard/crearAdministrador";
	}
			
	@PostMapping("/recuperarPassword")
	public String recuperarPassword(String correo, RedirectAttributes flash) {
		System.out.println(correo);
		
		if(!correo.contains("@")) {
			flash.addFlashAttribute("enviado", "Estimado usuario, el correo no es valido");
			return "redirect:/admin/password";
		}

		boolean enviado=correoService.recupearPasswordEmail(correo);
		if(!enviado) {
			flash.addFlashAttribute("enviado", "El correo no existe en la base de datos");
			return "redirect:/admin/password";
		}
		
		flash.addFlashAttribute("mensaje", "Enviamos informacion a su correo");
		return "redirect:/admin/login";
	}
	
	@GetMapping("/graficos")
	public String graficos(Model model) {
		
		return "dashboard/graficos";
	}
	
	@ModelAttribute("usuarios")
	public List<Usuario> listarUsuarios(){
		return iUsuarioService.listar();
	}
	@ModelAttribute("usuariosRegistrados")
	public Integer usuariosRegistrados(){
		return iUsuarioService.listar().size();
	}
	
}
