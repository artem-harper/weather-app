package org.weatherApp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.weatherApp.controller.utilControllers.SessionInterceptor;


@Configuration
@ComponentScan(
        basePackages = "org.weatherApp",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = TestConfig.class
        )
)
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final ThymeleafViewResolver thymeleafViewResolver;
    private final SessionInterceptor sessionInterceptor;

    public SpringConfig(ThymeleafViewResolver thymeleafViewResolver, SessionInterceptor sessionInterceptor) {
        this.thymeleafViewResolver = thymeleafViewResolver;
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafViewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/sign-in",
                        "/sign-up",
                        "/resources/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/webjars/**",
                        "/*.css",
                        "/*.js",
                        "/*.png",
                        "/*.jpg",
                        "/*.gif",
                        "/*.ico"
                )
                .order(1);
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
