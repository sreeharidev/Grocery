package com.noname.grocery;

import java.util.Arrays;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
  
 

@SpringBootApplication
public class GroceryApplication {
	private static final Logger logger = LogManager.getLogger(GroceryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GroceryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

        	logger.debug("Let's inspect the beans provided by Spring Boot:");
        	logger.error("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        
            // String result = encoder.encode("sreehari");
            //System.out.println(result);
            System.out.println("Matching");
           // System.out.println(Encryptors.text("sreehari", "salt"));
        };
    }

}
