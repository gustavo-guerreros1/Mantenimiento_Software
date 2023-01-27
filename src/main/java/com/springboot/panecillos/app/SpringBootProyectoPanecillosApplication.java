package com.springboot.panecillos.app;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class SpringBootProyectoPanecillosApplication implements CommandLineRunner {
	 
	@Autowired
	private JavaMailSender emailSender;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootProyectoPanecillosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hola mundo");
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() {
		final String username = "panaderia.panecillos.app@gmail.com";
        final String password = "EduardoCabro";
        
        String mensaje="Hola, bienvenido a PANECILLOS!\n"
        		+ "Lo primero que necesitamos es validar tu dirección de email, "
        		+ "así te daremos la mejor atención. Recibirás notificaciones de "
        		+ "nuestro productos,los productos más economicos del mercado, todo a un precio"
        		+ " accesible y algunas sorpresas que tenemos para tí."
        		+"\nIngresa a https://proyecto-panecillos.herokuapp.com";
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
                    InternetAddress.parse("academiacpp@gmail.com, jeisermano@gmail.com")
            );
            message.setSubject("Bienvenido a panecillos".toUpperCase());
            message.setText(mensaje);

            Transport.send(message);

            System.out.println("Enviado Correctamente");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	
}
