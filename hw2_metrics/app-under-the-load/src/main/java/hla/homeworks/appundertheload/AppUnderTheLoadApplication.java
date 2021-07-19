package hla.homeworks.appundertheload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AppUnderTheLoadApplication {

    @RequestMapping("/")
    public String home() {
        return "HLA World!";
    }


    public static void main(String[] args) {
        SpringApplication.run(AppUnderTheLoadApplication.class, args);
    }

}
