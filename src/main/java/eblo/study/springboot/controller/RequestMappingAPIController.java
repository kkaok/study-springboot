package eblo.study.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/mapping")
public class RequestMappingAPIController {

    /**
     * 모든 메소드 가능
     * @return
     */
    @RequestMapping("/requestMapping")
    public ResponseEntity<String> requestMapping() {
        log.debug("requestMapping call");
        return ResponseEntity.ok("requestMapping");
    }
    
    /**
     * @RequestMapping(method = RequestMethod.GET)와 동일  
     * @return
     */
    @GetMapping
    public ResponseEntity<String> getMapping() {
        log.debug("getMapping call");
        return ResponseEntity.ok("getMapping");
    }
    
    /**
     * @RequestMapping(method = RequestMethod.POST)와 동일  
     * @return
     */
    @PostMapping
    public ResponseEntity<String> postMapping() {
        log.debug("postMapping call");
        return ResponseEntity.ok("postMapping");
    }

    /**
     * @RequestMapping(method = RequestMethod.PUT)와 동일  
     * @return
     */
    @PutMapping
    public ResponseEntity<String> putMapping() {
        log.debug("putMapping call");
        return ResponseEntity.ok("putMapping");
    }

    /**
     * @RequestMapping(method = RequestMethod.DELETE)와 동일  
     * @return
     */
    @DeleteMapping
    public ResponseEntity<String> deleteMapping() {
        log.debug("deleteMapping call");
        return ResponseEntity.ok("deleteMapping");
    }

    /**
     * @RequestMapping(method = RequestMethod.GET, path="/test1")와 동일  
     * @return
     */
    @GetMapping("/test1")
    public ResponseEntity<String> getMappingTest() {
        log.debug("getMappingTest call");
        return ResponseEntity.ok("getMapping-test1");
    }
    
    /**
     * 복수개의 주소에 대한 예제  
     * @return
     */
    @GetMapping({"/test2","/test-mapping"})
    public ResponseEntity<String> getMappingTest2() {
        log.debug("getMappingTest2 call");
        return ResponseEntity.ok("getMapping-test2");
    }

}
