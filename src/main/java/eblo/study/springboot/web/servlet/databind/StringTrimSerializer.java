package eblo.study.springboot.web.servlet.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class StringTrimSerializer extends JsonSerializer<String> {
    
    @Override
    public void serialize(String text, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(text == null) jsonGenerator.writeString(text);
        else jsonGenerator.writeString(text.trim());
    }

}
