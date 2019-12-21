package net.javaguides.springbootsecurity.config;

import net.javaguides.springbootsecurity.helpers.storage.StorageProperties;
import net.javaguides.springbootsecurity.web.UploadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    private final Path rootLocation;

    @Autowired
    public AdditionalResourceWebConfiguration(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/deneme/**").addResourceLocations(rootLocation.toString());
    }
}