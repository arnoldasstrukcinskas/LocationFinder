package BigB_Project.LocationFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LocationFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationFinderApplication.class, args);
		System.out.println("hello");
	}

}
