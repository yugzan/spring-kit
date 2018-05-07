package org.yugzan.security.core;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MainSecurityConfiguration {

	@Configuration
    @Order(1)
    public static  class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter{

    	@Value("${account.roles:ADMIN, USER, SYSTEM}")
    	private String [] ROLES;

    	@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
			.antMatcher("/api/**")
			.authorizeRequests()
			.anyRequest().hasAnyRole(ROLES)
			.and()
			.apply(new CustomHttpConfigurerBeanConfigurer())
			.and()
			.anonymous().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

		}
	    @Bean
	    public AuthenticationEntryPoint unauthorizedEntryPoint() {
	        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    }

//	    @Bean
//	    public CustomHttpConfigurerBeanConfigurer applyCustomConfigurer() {
//	        return new CustomHttpConfigurerBeanConfigurer();
//	    }
    }
    
    @Configuration
    @Order(2)
    public static  class FromSecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			
			http
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.apply(new CustomHttpConfigurerBeanConfigurer())
				.and()
				.formLogin().and()
				.httpBasic();
		}
		private String username = "admin";
		private String password = "admin";
		private String role = "ADMIN";
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
			.inMemoryAuthentication()
				.withUser(username).password(password).roles(role);
		}
		
    }
    


}
