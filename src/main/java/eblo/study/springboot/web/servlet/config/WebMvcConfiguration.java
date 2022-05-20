package eblo.study.springboot.web.servlet.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import eblo.study.springboot.web.servlet.support.ObjectMapperUtil;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private static final String CLASSPATH_RESOURCE_LOCATIONS = "classpath:/static";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl 
                .maxAge(3600*24*7, TimeUnit.SECONDS) 
                .mustRevalidate();
        registry.addResourceHandler("/css/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/css/")
                .setCacheControl(cacheControl);
        registry.addResourceHandler("/images/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/images/")
                .setCacheControl(cacheControl);
        registry.addResourceHandler("/js/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/js/")
                .setCacheControl(cacheControl);
        registry.addResourceHandler("/html/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/html/")
                .setCacheControl(cacheControl);
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"favicon.ico")
                .setCacheControl(cacheControl);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/whoami").setViewName("whoami");
    }

    @Autowired(required = false)
    private Converter<?, ?>[] converters;
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        if(converters != null) {
            for(final Converter<?, ?> converter : converters) {
                registry.addConverter(converter);
            }
        }
        NumberStyleFormatter numberFormatter = new NumberStyleFormatter();
        numberFormatter.setPattern("#,###,###,###.##");
        registry.addFormatter(numberFormatter);
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(ObjectMapperUtil.getObjectMapper());
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        super.addDefaultHttpMessageConverters(converters);
    }

}
