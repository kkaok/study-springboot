package eblo.study.springboot.web.servlet.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringTrimConverter implements Converter<String, String> {
    
    @Override
    public String convert(String source) {
        if(source == null) return source;
        return source.trim();
    }

}