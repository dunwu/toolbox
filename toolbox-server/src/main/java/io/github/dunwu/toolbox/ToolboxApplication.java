package io.github.dunwu.toolbox;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;

@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages = "io.github.dunwu", exclude = {QuartzAutoConfiguration.class})
public class ToolboxApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolboxApplication.class, args);
    }
}
