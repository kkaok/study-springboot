package eblo.study.springboot.web.servlet.convert;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eblo.study.springboot.web.servlet.support.DateUtil;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    
    @Override
    public LocalDateTime convert(String source) {
        
        try {
            return LocalDateTime.parse(source, DateUtil.getDateTimeFormatter());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
}