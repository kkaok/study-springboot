package eblo.study.springboot.web.servlet.databind;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eblo.study.springboot.web.servlet.constant.DateFormatConstant;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {

    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String date = jsonParser.getText();
        try {
            Date parsedDate = DateFormatConstant.DATETIME_FORMAT.parse(date);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
