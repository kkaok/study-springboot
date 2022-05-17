package eblo.study.springboot.web.servlet.convert;

import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eblo.study.springboot.web.servlet.constant.DateFormatConstant;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    
    @Override
    public Date convert(String source) {
        
        try {
            return DateFormatConstant.DATE_FORMAT.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } 
    }
}