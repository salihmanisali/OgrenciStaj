package net.javaguides.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * @author Salih Efe
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{   
	
	@Autowired
    private MessageSource messageSource;

	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin/home").setViewName("adminhome");

        registry.addViewController("/upload").setViewName("upload");

        registry.addViewController("/ogrenci").setViewName("ogrenci");
        registry.addViewController("/ogrenciler").setViewName("ogrenciler");

        registry.addViewController("/okul").setViewName("okul");
        registry.addViewController("/okullar").setViewName("okullar");

        registry.addViewController("/firma").setViewName("firma");
        registry.addViewController("/firmalar").setViewName("firmalar");

        registry.addViewController("/ogretmen").setViewName("ogretmen");
        registry.addViewController("/ogretmenler").setViewName("ogretmenler");
	}
	
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }
	
//    @Bean
//	public SpringSecurityDialect securityDialect() {
//	    return new SpringSecurityDialect();
//	}
}
