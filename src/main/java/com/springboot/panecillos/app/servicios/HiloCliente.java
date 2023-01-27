package com.springboot.panecillos.app.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.panecillos.app.models.domain.Usuario;
import com.springboot.panecillos.app.service.CorreoService;
import com.springboot.panecillos.app.service.IUsuarioService;

public class HiloCliente extends Thread{
	
	@Autowired
	private IUsuarioService iUsuarioService; 
	
	@Autowired
	private CorreoService enviarCorreoService; 
	
	@PostMapping("/signUp")
	public String procesarFormulario(Usuario usuario, RedirectAttributes flash) {
		String pwdPlano=usuario.getPassword();
		
		if(usuario.getEmail().isEmpty()
				|| usuario.getPassword().isEmpty() 
				|| !usuario.getEmail().contains("@")
				|| usuario.getNombre().isEmpty()
				|| usuario.getApellido().isEmpty()) {
			flash.addFlashAttribute("error2", "Faltan datos");
			return "redirect:/signUp";
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
		

		if(usuario==null) {
			flash.addFlashAttribute("error", "El usuario es incorrecto");
			return "redirect:/login";
		}
		return "redirect:/login";
	}
}
