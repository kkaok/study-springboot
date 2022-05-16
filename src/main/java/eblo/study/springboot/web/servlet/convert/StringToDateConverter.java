package eblo.study.springboot.web.servlet.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    
    private final static String DATE_FORMAT = "yyyyy-MM-dd";
    
    @Override
    public Date convert(String source) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } 
    }
}