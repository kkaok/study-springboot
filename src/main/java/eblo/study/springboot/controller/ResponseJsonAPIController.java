package eblo.study.springboot.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/response/json")
public class ResponseJsonAPIController {

    /**
     * jackson annotation 테스트    
     * @return
     */
    @PostMapping()
    public ResponseEntity<JsonTest> jsonTest(@RequestBody final JsonTest body) {
        log.debug("requestParamRequiredFalse call : "+body);
        return ResponseEntity.ok(body);
    }

    @Getter
    @Setter
    @ToString
    //@JsonRootName(value="user") // SerializationFeature.WRAP_ROOT_VALUE, DeserializationFeature.UNWRAP_ROOT_VALUE enable 필요 
    @JsonPropertyOrder({ "userId", "name" , "created" }) // 순서 정의 
    @JsonInclude(Include.NON_NULL) // 값이 null 이 아닌 것만 포함한다. 
    public static class JsonTest {

        /**
         * json으로 역직렬화할 때 testId라는 필드명으로 한다. 
         */
        @JsonProperty(value="testId")
        private String id;

        /**
         * 값을 받지만 역직렬화는 하지 않는다. 
         */
        @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
        private String name;
        
        /**
         * 이 필드는 제외 
         */
        @JsonIgnore
        private Boolean test;
        
        private String emptyField;
        
        /**
         * yyyy-MM-dd 포맷으로 전달 된 값을 직렬화한다. 
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Asia/Seoul")
        private Date created;

        public JsonTest() {
            super();
        }

        public JsonTest(String id, String name, Boolean test, Date created) {
            super();
            this.id = id;
            this.name = name;
            this.test = test;
            this.created = created;
        }

        /**
         * "userId"라는 이름으로 받아서 id에 값을 넣는다. 
         * @param id
         * @param name
         */
        @JsonCreator
        public JsonTest(@JsonProperty("userId") String id,@JsonProperty("name") String name) {
            super();
            this.id = id;
            this.name = name;
        }
    }

}
