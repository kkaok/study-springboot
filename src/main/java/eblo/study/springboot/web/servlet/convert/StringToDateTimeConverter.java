package eblo.study.springboot.web.servlet.convert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateTimeConverter implements Converter<String, Timestamp> {
    
    private final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    @Override
    public Timestamp convert(String source) {
        SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Date parsedDate = format.parse(source);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } 
    }

}