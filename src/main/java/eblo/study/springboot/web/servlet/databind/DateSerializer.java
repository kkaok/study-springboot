package eblo.study.springboot.web.servlet.databind;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eblo.study.springboot.web.servlet.support.DateUtil;

public class DateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            jsonGenerator.writeString(DateUtil.getDateFormat().format(date));
        } catch (DateTimeParseException e) {
            jsonGenerator.writeString("");
        }
    }

}
