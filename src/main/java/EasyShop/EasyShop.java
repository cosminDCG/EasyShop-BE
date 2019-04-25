package EasyShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan({"EasyShop"})
@ComponentScan({"EasyShop"})
@EnableJpaRepositories("EasyShop")
@EntityScan(basePackages = {"EasyShop"})
@EnableScheduling
@Configuration
public class EasyShop {
    public static void main(String[] args) {
        SpringApplication.run(EasyShop.class, args);
    }
}
