package sg.edu.ntu.bus_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("http://localhost:5173") // VITE + React frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials like cookies or authorization headers

            }
        };
    }
}

// credits to Group 2 for the solution
// https://github.com/Mmanyuu/Project-Split-And-Share/blob/main/src/main/java/sg/edu/ntu/split_and_share/config/CorsConfig.java