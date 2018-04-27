package org.yugzan.skit.ddos.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.yugzan.skit.ddos.DefaultDDoSOperator;

/**
 * @author yongzan
 * @date 2018/04/27 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableDDoSFilter
@Configuration
@Import(DefaultDDoSOperator.class)
public @interface EnableDefaultDDoSFilter {

}
