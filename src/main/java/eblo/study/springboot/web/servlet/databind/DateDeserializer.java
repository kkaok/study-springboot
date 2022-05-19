package eblo.study.springboot.web.servlet.databind;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eblo.study.springboot.web.servlet.support.DateUtil;

public class DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return DateUtil.getDateFormat().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
