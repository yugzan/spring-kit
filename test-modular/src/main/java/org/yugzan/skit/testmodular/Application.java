package org.yugzan.skit.testmodular;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.yugzan.security.annotation.EnableSecirityConfigKit;

/**
 * @author yongzan
 * @date 2018/04/26
 */
@SpringBootApplication
@EnableSecirityConfigKit
public class Application implements CommandLineRunner {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		String [] beans = context.getBeanDefinitionNames();
		Arrays.sort(beans);
		for(String name: beans) {
			System.out.println(name);
		}
		System.out.println("Bean count("+ beans.length +")");
	}
	
	@Override
	public void run(String... arg0) throws Exception {

	}

}
