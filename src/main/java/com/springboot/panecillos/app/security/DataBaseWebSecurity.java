package com.springboot.panecillos.app.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//@EnableWebSecurity
public class DataBaseWebSecurity {
	
	/*
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select email, password, enabled from usuario where email=?")
			.authoritiesByUsernameQuery("select usuario.email, roles.rol "
					+ "from usuario inner join roles on usuario.id=roles.idusuario where usuario.email=?;");  //configuracion para usar la base de datos por defecto
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(
					"/css/**",
					"/images/**",
					"/fonts/**",
					"/js/**",
					"/uploads/**"
					).permitAll()
			.antMatchers(
					"/",
					"/index",
					"/admin/login",
					"/admin/password",
					"/productos",
					"filtrarCategoria/**",
					"/signUp"
					).permitAll()
			.antMatchers("/admin/clientes").hasAuthority("ADMINISTRADOR")
			.antMatchers("/carrito").hasAuthority("ADMINISTRADOR")
			.anyRequest().authenticated()
			.and().formLogin().permitAll()
			.and().formLogin().loginPage("/singUp");
	}
	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	*/

}
