package test.photos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
    }

    @Configuration
    public static class StaticResourceConfiguration extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter  {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/res/**")
                    .addResourceLocations("file://" + PhotosController.PATH)
                    .addResourceLocations("classpath:/resources/public")
                    .addResourceLocations("classpath:/resources")
                    .addResourceLocations("classpath:/**");
            super.addResourceHandlers(registry);
            //registry.addViewController("/").setViewName("forward:index.html");

        }
    }

}
