package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.AuthenticationFilter;
import com.example.demo.filter.AuthorizationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
		authenticationFilter.setFilterProcessesUrl("/api/login");
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/users/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/**").permitAll();

		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/roles/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/roles/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/roles/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/roles/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/roles/**").permitAll();

		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/products/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/products/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/products/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/products/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/products/**").permitAll();

		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/reservations/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/reservations/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/reservations/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/reservations/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/reservations/**").permitAll();

		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(authenticationFilter);
		http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
