package org.yugzan.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.Filter;

import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeforFilterBean {
	
	Class<? extends Filter> value() default FilterSecurityInterceptor.class;

}
