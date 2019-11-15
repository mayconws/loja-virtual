package com.loja.virtual;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* Atenticação de usuários em memória
		 		 
		auth.inMemoryAuthentication().withUser("user")
		.password(new BCryptPasswordEncoder().encode("123")).roles("USER")
		.and().withUser("admin")
		.password(new BCryptPasswordEncoder()
		.encode("admin")).roles("USER", "ADMIN"); */
		
		/* Autenticação de usuários no banco de dados */
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
				"select email as username, senha as password, 1 as enable from funcionario where email=?")
		.authoritiesByUsernameQuery(
				"select funcionario.email as username, papel.nome as authority from permissoes_funcionario inner join funcionario on funcionario.id=permissoes_funcionario.funcionario_id inner join papel on permissoes_funcionario.papel_id=papel.id where funcionario.email=?")
		.passwordEncoder(new BCryptPasswordEncoder());

}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("//**").hasAnyAuthority("Vendedor","Gerente").
		antMatchers("//**").hasAnyAuthority("Gerente").and().formLogin()
				.loginPage("/login").permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
				.exceptionHandling().accessDeniedPage("/negado");

	}

}
