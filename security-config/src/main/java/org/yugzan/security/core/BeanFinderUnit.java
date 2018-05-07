package org.yugzan.security.core;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class BeanFinderUnit  {

	private final static Logger logger = LoggerFactory.getLogger(BeanFinderUnit.class);

	private ApplicationContext context;
	
	public BeanFinderUnit(ApplicationContext context) {
		this.context = context;
	}

//	@SuppressWarnings("unchecked")
//	public <T> T runBeanMatcher(Class<? extends Annotation> clazz) {
//		String [] beans = context.getBeanNamesForAnnotation(clazz);
//		for(String name: beans) {
//			logger.error("{}:{}",clazz.getSimpleName(), name);
//			return (T) getBean(name);
//		}
//
//		return null;
//		
//	}
//	private static void checkApplicationContext() {
//		if (null == context) {
//			throw new IllegalStateException("ApplicationContext is null.");
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name) {
		return (T) context.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<?> clazz) {
		return (T) context.getBeansOfType(clazz);

	}
}
