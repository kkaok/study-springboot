package eblo.study.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controller/mapping")
public class RequestMappingAPIController {

    @RequestMapping("/requestMapping")
    public ResponseEntity<String> requestMapping() {
        return ResponseEntity.ok("requestMapping");
    }
    
    @GetMapping
    public ResponseEntity<String> getMapping() {
        return ResponseEntity.ok("getMapping");
    }
    
    @PostMapping
    public ResponseEntity<String> postMapping() {
        return ResponseEntity.ok("postMapping");
    }

    @PutMapping
    public ResponseEntity<String> putMapping() {
        return ResponseEntity.ok("putMapping");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMapping() {
        return ResponseEntity.ok("deleteMapping");
    }

    @GetMapping("/test1")
    public ResponseEntity<String> getMappingTest() {
        return ResponseEntity.ok("getMapping-test1");
    }
    
    /**
     * 복수개의 주소에 대한 예제  
     * @return
     */
    @GetMapping({"/test2","/test-mapping"})
    public ResponseEntity<String> getMappingTest2() {
        return ResponseEntity.ok("getMapping-test2");
    }

}
