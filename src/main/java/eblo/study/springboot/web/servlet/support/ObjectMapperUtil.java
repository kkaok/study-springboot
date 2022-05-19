package eblo.study.springboot.web.servlet.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import eblo.study.springboot.web.servlet.databind.DateDeserializer;
import eblo.study.springboot.web.servlet.databind.DateSerializer;
import eblo.study.springboot.web.servlet.databind.LocalDateDeserializer;
import eblo.study.springboot.web.servlet.databind.LocalDateSerializer;
import eblo.study.springboot.web.servlet.databind.LocalDateTimeDeserializer;
import eblo.study.springboot.web.servlet.databind.LocalDateTimeSerializer;
import eblo.study.springboot.web.servlet.databind.StringTrimDeserializer;
import eblo.study.springboot.web.servlet.databind.StringTrimSerializer;
import eblo.study.springboot.web.servlet.databind.TimestampDeserializer;
import eblo.study.springboot.web.servlet.databind.TimestampSerializer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperUtil {

    private static final ObjectMapper objectMapper = objectMapper();

    private ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // unknown field 무시 
        // 매핑시 필드만 사용. 
        om.setVisibility(om.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        
        om.setSerializationInclusion(Include.NON_EMPTY); // 빈 값 제거 
        //om.enable(SerializationFeature.WRAP_ROOT_VALUE); // root를 사용할 때 
        //om.enable(DeserializationFeature.UNWRAP_ROOT_VALUE); // root로 값을 넘길 때 사용. 

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addSerializer(Date.class, new DateSerializer());
        simpleModule.addDeserializer(Date.class, new DateDeserializer());
        simpleModule.addSerializer(Timestamp.class, new TimestampSerializer());
        simpleModule.addDeserializer(Timestamp.class, new TimestampDeserializer());
        simpleModule.addSerializer(String.class, new StringTrimSerializer());
        simpleModule.addDeserializer(String.class, new StringTrimDeserializer());
        om.registerModule(simpleModule);
        return om;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
    public static String getJsonString(Object obj) {
        try {
        	if(obj != null)  return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getObject(String jsonStr, Class<T> type) {
        try {
            return objectMapper.readValue(jsonStr, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObjectFromFile(String filePath, Class<T> type){
        try {
            return objectMapper.readValue(new File(filePath), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveStringToJsonFile(Object json, String jsonFilePath) {
        File file = new File(jsonFilePath);
        try(FileOutputStream fileOutputStream = new FileOutputStream(file, false)){
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(fileOutputStream, json);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
