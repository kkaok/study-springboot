package eblo.study.springboot.web.servlet.convert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eblo.study.springboot.web.servlet.support.DateUtil;

@Component
public class StringToDateTimeConverter implements Converter<String, Timestamp> {
    
    
    @Override
    public Timestamp convert(String source) {
        try {
            Date parsedDate = DateUtil.getDateTimeFormat().parse(source);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } 
    }

}