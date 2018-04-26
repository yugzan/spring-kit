package org.yugzan.skit.testmodular;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yongzan
 * @date 2018/04/26
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args).start();
	}
	
	@Override
	public void run(String... arg0) throws Exception {

	}

}
