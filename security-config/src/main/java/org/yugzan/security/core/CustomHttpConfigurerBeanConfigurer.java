package org.yugzan.security.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.yugzan.security.annotation.HttpConfigurerBean;

public class CustomHttpConfigurerBeanConfigurer
		extends AbstractHttpConfigurer<CustomHttpConfigurerBeanConfigurer, HttpSecurity> {
	private final static Logger logger = LoggerFactory.getLogger(CustomHttpConfigurerBeanConfigurer.class);

	@Override
	public void init(HttpSecurity http) throws Exception {
		logger.info("CustomHttpConfigurerBeanConfigurer#init");
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        BeanFinderUnit unit = new BeanFinderUnit(context);
        String[] beans =  context.getBeanNamesForAnnotation(HttpConfigurerBean.class);
        
        for(String name: beans) {
    		logger.info("Add custom Configurer:[{}]",name);
        	http.apply( unit.getBean(name));
        } 
		super.init(http);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

	}

	
//	public static CustomHttpConfigurerBeanConfigurer addCustomConfigurer() {
//		return new CustomHttpConfigurerBeanConfigurer();
//	}
}
