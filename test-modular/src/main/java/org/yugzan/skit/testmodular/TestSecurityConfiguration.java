package org.yugzan.skit.testmodular;


import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.yugzan.skit.ddos.DDoSOperator;
import org.yugzan.skit.ddos.DDoSfilter;
import org.yugzan.skit.ddos.DefaultDDoSOperator;
import org.yugzan.skit.ddos.annotation.EnableDDoSFilter;

/**
 * @author yongzan
 * @date 2018/04/27
 */
//@Configuration
////@EnableDDoSFilter
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class TestSecurityConfiguration  { //extends WebSecurityConfigurerAdapter

//	private String username = "admin";
//	private String password = "admin";
//	private String role = "ADMIN";
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.inMemoryAuthentication()
//			.withUser(username).password(password).roles(role);
//		super.configure(auth);
//	}
//
//	@Configuration
//    @Order(1)
//    public static  class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter{
//
//    	@Value("${account.roles:ADMIN, USER, SYSTEM}")
//    	private String [] ROLES;
//    	
////    	@Autowired
////    	private DDoSfilter ddoSfilter;
//    	
//    	@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.csrf().disable()
//			.antMatcher("/api/**")
//			.authorizeRequests()
//			.anyRequest()	.hasAnyRole(ROLES)
//			.and()
//			.anonymous().disable()
//			.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
////			http.addFilterBefore(ddoSfilter, BasicAuthenticationFilter.class);
//			http.addFilterBefore(new TestAuthenticationFilter(), BasicAuthenticationFilter.class);
//
//		}
//	    @Bean
//	    public AuthenticationEntryPoint unauthorizedEntryPoint() {
//	        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//	    }
//
//    }
//    
//    @Configuration
//    @Order(2)
//    public static  class FromSecurityConfiguration extends WebSecurityConfigurerAdapter{
//    	
////    	@Autowired
////    	private DDoSfilter ddoSfilter;
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http
//				.authorizeRequests()
//				.anyRequest().authenticated()
//				.and()
//				.csrf().disable()
//				
////				.addFilterBefore( ddoSfilter , UsernamePasswordAuthenticationFilter.class)
//				.formLogin().and()
//				.httpBasic();
//		}
//    }
//
//	@Bean
//	public DDoSOperator getoperator() {
//		return new DefaultDDoSOperator();
////		return new TestCustomOperator("https://tw.news.yahoo.com/");
//	}
}
