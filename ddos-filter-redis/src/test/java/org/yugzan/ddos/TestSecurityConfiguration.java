package org.yugzan.ddos;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled= true)
public class TestSecurityConfiguration{


    @Configuration
    public static  class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter{
    	
    	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    	
    	@Value("${account.roles:ADMIN, USER, SYSTEM}")
    	private String [] ROLES;

    	@Autowired
    	private DDoSfilter ddos;
    	
    	@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.antMatcher("/api/**")
			.authorizeRequests()
			.anyRequest()	.hasAnyRole(ROLES)
			.and()
			.anonymous().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
			http.addFilterBefore(ddos, BasicAuthenticationFilter.class);
	        http.addFilterBefore(new TestAuthenticationFilter(), BasicAuthenticationFilter.class);

		}

	    @Bean
	    public AuthenticationEntryPoint unauthorizedEntryPoint() {
	        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    }
    } 
    
}
