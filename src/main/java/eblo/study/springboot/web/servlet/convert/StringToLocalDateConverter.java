package eblo.study.springboot.web.servlet.convert;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eblo.study.springboot.web.servlet.support.DateUtil;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    
    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, DateUtil.getDateFormatter());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
}