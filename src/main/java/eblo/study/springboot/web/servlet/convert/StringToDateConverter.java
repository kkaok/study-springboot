package eblo.study.springboot.web.servlet.convert;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eblo.study.springboot.web.servlet.support.DateUtil;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    
    @Override
    public Date convert(String source) {
        try {
            return DateUtil.getDateFormat().parse(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
}