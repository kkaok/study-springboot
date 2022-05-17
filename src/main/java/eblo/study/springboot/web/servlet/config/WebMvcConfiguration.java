package eblo.study.springboot.web.servlet.config;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import eblo.study.springboot.web.servlet.databind.DateDeserializer;
import eblo.study.springboot.web.servlet.databind.DateSerializer;
import eblo.study.springboot.web.servlet.databind.LocalDateDeserializer;
import eblo.study.springboot.web.servlet.databind.LocalDateSerializer;
import eblo.study.springboot.web.servlet.databind.LocalDateTimeDeserializer;
import eblo.study.springboot.web.servlet.databind.LocalDateTimeSerializer;
import eblo.study.springboot.web.servlet.databind.StringTrimDeserializer;
import eblo.study.springboot.web.servlet.databind.StringTrimSerializer;
import eblo.study.springboot.web.servlet.databind.TimestampDeserializer;
import eblo.study.springboot.web.servlet.databind.TimestampSerializer;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private static final String CLASSPATH_RESOURCE_LOCATIONS = "classpath:/static";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/css/").setCachePeriod(3600);
        registry.addResourceHandler("/images/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/images/").setCachePeriod(3600);
        registry.addResourceHandler("/js/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/js/").setCachePeriod(3600);
        registry.addResourceHandler("/html/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"/html/").setCachePeriod(3600);
        registry.addResourceHandler("/favicon.ico"  ).addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS+"favicon.ico"   ).setCachePeriod(3600);
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

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorParameter(false)
//        .ignoreAcceptHeader(true)
//        .defaultContentType(MediaType.APPLICATION_JSON);
//    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // unknown field 무시 
        // 매핑시 필드만 사용. 
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        
        objectMapper.setSerializationInclusion(Include.NON_EMPTY); // 빈 값 제거 
        //objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE); // root를 사용할 때 
        //objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE); // root로 값을 넘길 때 사용. 

        //serializer, deserializer설정 
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addSerializer(Date.class, new DateSerializer());
        simpleModule.addDeserializer(Date.class, new DateDeserializer());
        simpleModule.addSerializer(Timestamp.class, new TimestampSerializer());
        simpleModule.addDeserializer(Timestamp.class, new TimestampDeserializer());
        simpleModule.addSerializer(String.class, new StringTrimSerializer());
        simpleModule.addDeserializer(String.class, new StringTrimDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
         
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        super.addDefaultHttpMessageConverters(converters);
    }

}
