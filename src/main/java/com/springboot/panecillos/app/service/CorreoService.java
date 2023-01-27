package com.springboot.panecillos.app.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.panecillos.app.models.domain.Administrador;

@Service
public class CorreoService {
	final String username = "panaderia.panecillos.app@gmail.com";
    final String password = "EduardoCabro";
    
    @Autowired
    private IAdministradorService administradorService;
    
	public void sendEmail(String correo) {
		
        
        String mensaje="Hola, bienvenido a PANECILLOS!\n"
        		+ "Lo primero que necesitamos es validar tu dirección de email, "
        		+ "así te daremos la mejor atención. Recibirás notificaciones de "
        		+ "nuestro productos,los productos más economicos del mercado, todo a un precio"
        		+ " accesible y algunas sorpresas que tenemos para tí."
        		+"\nIngresa a https://proyecto-panecillos.herokuapp.com/confirmarCorreo";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("panaderia.panecillos.app@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correo+", academiacpp@gmail.com")
            );
            message.setSubject("Bienvenido a panecillos".toUpperCase());
            message.setText(mensaje);

            Transport.send(message);

            System.out.println("Enviado Correctamente");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	public boolean recupearPasswordEmail(String correo) {
		Administrador admin=administradorService.buscarPorCorreo(correo);
		if(admin==null) {
			return false;
		}
        String mensaje="Estimado"+" "+ admin.getNombre()+" sus datos son los siguiente: \n"
        		+ "Correo : "+admin.getCorreo()+" \n"
        		+"Password: "+admin.getPassword()+" \n"
        		+"Rol: "+admin.getRol()+"\n";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("panaderia.panecillos.app@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correo+", academiacpp@gmail.com")
            );
            message.setSubject("Recuperación de cuenta PANECILLOS".toUpperCase());
            message.setText(mensaje);

            Transport.send(message);

            System.out.println("Enviado Correctamente");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        
	}
}
