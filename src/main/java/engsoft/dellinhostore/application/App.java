package engsoft.dellinhostore.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "engsoft.dellinhostore.controller" })
public class App {
    public static void main( String[] args ) {
    	System.out.println("Comecei\n\n\n\n");
    	System.out.println("DATABASE_CUSTOM_URL : " + System.getenv("DATABASE_CUSTOM_URL"));
    	System.out.println("DATABASE_USERNAME : " + System.getenv("DATABASE_USERNAME"));
    	System.out.println("DATABASE_PASSWORD : " + System.getenv("DATABASE_PASSWORD"));
    	SpringApplication.run(App.class, args);
    }  
}