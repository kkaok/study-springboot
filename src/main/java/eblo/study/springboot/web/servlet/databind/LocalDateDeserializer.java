package eblo.study.springboot.web.servlet.databind;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eblo.study.springboot.web.servlet.constant.DateFormatConstant;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(jsonParser.getText(), DateFormatConstant.DATE_FORMATTER);
    }

}
