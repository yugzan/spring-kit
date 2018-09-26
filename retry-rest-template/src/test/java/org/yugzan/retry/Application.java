package org.yugzan.retry;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		String [] beans = context.getBeanDefinitionNames();
		Arrays.sort(beans);
		for(String name: beans) {
			System.out.println(name);
		}
		System.out.println("Bean count("+ beans.length +")");
	}
}
