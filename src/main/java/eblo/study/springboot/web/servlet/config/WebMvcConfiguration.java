package eblo.study.springboot.web.servlet.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(false)
        .ignoreAcceptHeader(true)
        .defaultContentType(MediaType.APPLICATION_JSON);
    }
    
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // unknown field 무시 
//        //objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
//        objectMapper.setSerializationInclusion(Include.NON_EMPTY); // 불필요한값을 제거하기 위해 NOT_EMPTY로 설정
//        //objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE); // root를 사용할 때 
//        //objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE); // root로 값을 넘길 때 사용. 
//        
//        //날짜포맷 설정
//        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        //dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
//        //objectMapper.setDateFormat(dateFormat);
//
//        // serializer, deserializer설정 
//        //SimpleModule simpleModule = new SimpleModule();
//        //simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
//        //simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//        //objectMapper.registerModule(simpleModule);
//        return objectMapper;
//    }
//         
//    @Bean
//    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        jsonConverter.setObjectMapper(objectMapper());
//        return jsonConverter;
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(customJackson2HttpMessageConverter());
//        super.addDefaultHttpMessageConverters(converters);
//    }

}
