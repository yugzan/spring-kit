package org.yugzan.skit.testmodular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yugzan.security.annotation.BeforFilterBean;
import org.yugzan.skit.ddos.DDoSOperator;
import org.yugzan.skit.ddos.DDoSfilter;
import org.yugzan.skit.ddos.DefaultDDoSOperator;
import org.yugzan.skit.ddos.annotation.EnableDDoSFilter;

//@Configuration
//@EnableDDoSFilter
public class AppConfig {
	
//	@Autowired
//	private DDoSfilter filter;
	
//	@BeforFilterBean
	public void configure(HttpSecurity http) throws Exception {
//		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
