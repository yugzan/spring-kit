package org.yugzan.security.core;

import javax.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.yugzan.security.annotation.BeforFilterBean;
import org.yugzan.security.annotation.HttpConfigurerBean;

@HttpConfigurerBean
public class CustomBeforeFilterConfigurer extends AbstractHttpConfigurer<CustomBeforeFilterConfigurer, HttpSecurity> {
	private final static Logger logger = LoggerFactory.getLogger(CustomBeforeFilterConfigurer.class);
	
	@Override
	public void init(HttpSecurity http) throws Exception {
		super.init(http);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("CustomBeforeFilterConfigurer#configure");
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        String[] beans =  context.getBeanNamesForAnnotation(BeforFilterBean.class);
        for(String name: beans) {
        	BeforFilterBean theAnn = context.findAnnotationOnBean(name, BeforFilterBean.class);
    		logger.info("Add before Bean:[{} on {}]",name, theAnn.value().getSimpleName());
        	http.addFilterBefore((Filter) context.getBean(name), theAnn.value());
        }

// TODO I have no idea how to ignore some bean to get Method of Annotation Present
// Requested bean is currently in creation: Is there an unresolvable circular reference?
//      String[] temps = context.getBeanDefinitionNames();
//      for(String name: temps) {
//  		Object obj = context.getBean(name);
//          Class<?> objClz = obj.getClass();
//          if (org.springframework.aop.support.AopUtils.isAopProxy(obj)) {
//              objClz = org.springframework.aop.support.AopUtils.getTargetClass(obj);
//          }
//    
//          for (Method m : objClz.getDeclaredMethods()) {
//              if (m.isAnnotationPresent(BeforFilterBean.class)) {
//          		logger.info("isAnnotationPresent:{} on {}",name, m.getName());
//              }
//          }
//      }
	}

}
