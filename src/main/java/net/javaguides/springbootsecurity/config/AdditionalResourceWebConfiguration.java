package net.javaguides.springbootsecurity.config;

import net.javaguides.springbootsecurity.web.UploadController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/deneme/**").addResourceLocations(UploadController.UPLOADED_FOLDER);
    }
}