package xhdProject.ms.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class MainController {
    public static void main(String[] args) {
        SpringApplication.run(DemoController.class, args);
    }
}
