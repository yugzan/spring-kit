package org.yugzan.skit.testmodular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yugzan.security.annotation.HttpConfigurerBean;
import org.yugzan.skit.ddos.DDoSOperator;
import org.yugzan.skit.ddos.DDoSfilter;
import org.yugzan.skit.ddos.DefaultDDoSOperator;
import org.yugzan.skit.ddos.annotation.EnableDDoSFilter;

@HttpConfigurerBean
@EnableDDoSFilter
public class DDoSConfigurer extends AbstractHttpConfigurer<DDoSConfigurer, HttpSecurity>{

	@Autowired
	private DDoSfilter filter;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}

	
	@Bean
	public DDoSOperator defaultoper() {
		return new DefaultDDoSOperator();
	}
}
