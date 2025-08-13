package teste.api.rest.back.end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
@RestController
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"teste.api.rest.back.end.Repository"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"teste.*"})
public class TesteTecnicoEstGioBackendApplication {



    public static void main(String[] args) {
        SpringApplication.run(TesteTecnicoEstGioBackendApplication.class, args);
    }

}
