package eblo.study.springboot.web.servlet.convert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eblo.study.springboot.web.servlet.constant.DateFormatConstant;

@Component
public class StringToDateTimeConverter implements Converter<String, Timestamp> {
    
    
    @Override
    public Timestamp convert(String source) {
        try {
            Date parsedDate = DateFormatConstant.DATETIME_FORMAT.parse(source);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } 
    }

}