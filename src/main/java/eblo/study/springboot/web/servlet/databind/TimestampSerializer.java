package eblo.study.springboot.web.servlet.databind;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eblo.study.springboot.web.servlet.support.DateUtil;

public class TimestampSerializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            jsonGenerator.writeString(DateUtil.getDateTimeFormat().format(dateTime));
        } catch (DateTimeParseException e) {
            jsonGenerator.writeString("");
        }
    }

}
