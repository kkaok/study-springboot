package eblo.study.springboot.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/response")
public class ResponseAPIController {

    /**
     * Content-type 정보가 없을 때 String으로 받기  
     * @return
     */
    @PostMapping("/contentTypeNone")
    public ResponseEntity<String> contentTypeNoneString(@RequestBody final String body) {
        log.debug("contentTypeNoneString call : "+body);
        return ResponseEntity.ok(body);
    }

    /**
     * Content-type 정보가 없을 때 객체로 받기 - 에러 발생   
     * @param body
     * @return
     */
    @PostMapping(path="/contentTypeNoneObject")
    public ResponseEntity<ResponseTest> contentTypeNone(@RequestBody final ResponseTest body) {
        log.debug("contentTypeNone call : "+body);
        return ResponseEntity.ok(body);
    }

    /**
     * Content-type : application/json, 결과는 xml   
     * @param body
     * @return
     */
    @PostMapping(path="/producesXml",  produces="application/xml; charset=UTF8")
    public ResponseEntity<ResponseTest> producesXml(@RequestBody final ResponseTest body) {
        log.debug("producesXml call : "+body);
        return ResponseEntity.ok(body);
    }

    /**
     * Content-type : application/json, 결과는 json   
     * @param body
     * @return
     */
    @PostMapping(path="/producesJson",  produces="application/json; charset=UTF8")
    public ResponseEntity<ResponseTest> producesJson(@RequestBody final ResponseTest body) {
        log.debug("producesJson call : "+body);
        return ResponseEntity.ok(body);
    }

    @Getter
    @Setter
    @ToString
    public static class ResponseTest {
        
        private String id;
        private String name;
        private Boolean test;
        private Date created;

        public ResponseTest() {
            super();
        }

        public ResponseTest(String id, String name, boolean test, Date created) {
            super();
            this.id = id;
            this.name = name;
            this.test = test;
            this.created = created;
        }
    }

}
