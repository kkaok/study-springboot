package eblo.study.springboot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/path")
public class PathVariableAPIController {

    /**
     * 단순 매핑 테스트 
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> simple(@PathVariable String id) {
        log(id);
        return ResponseEntity.ok(id);
    }

    /**
     * required false test 
     * @param id
     * @return
     */
    @GetMapping(value = { "/requiredfalse", "/requiredfalse/{id}"})
    public ResponseEntity<String> requiredfalse(@PathVariable(required = false) String id) {
        return simple(id);
    }
    
    /**
     * optional test 
     * @param id
     * @return
     */
    @GetMapping(value = { "/optional", "/optional/{id}"})
    public ResponseEntity<String> requiredfalseOptional(@PathVariable(required = false) Optional<String> id) {
        String path = id.orElse("none");
        log(path);
        return ResponseEntity.ok(path);
    }
    
    /**
     * pathVariable 복수개인 경우 테스트 
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/{id}/{name}")
    public ResponseEntity<Map<String, String>> multiplePaths(@PathVariable String id, @PathVariable String name) {
        Map<String, String> paths = new HashMap<>();
        paths.put("id", id);
        paths.put("name", name);
        log(paths.toString());
        return ResponseEntity.ok(paths);
    }

    /**
     * pathVariable을 Map으로 받는 경우 
     * @param paths
     * @return
     */
    @GetMapping("/map/{id}/{name}")
    public ResponseEntity<Map<String, String>> multipleMapPaths(@PathVariable Map<String, String> paths) {
        log(paths.toString());
        return ResponseEntity.ok(paths);
    }
    
    /**
     * pathVariable을 Object로 받는 경우 
     * @param paths
     * @return
     */
    @GetMapping("/object/{id}/{name}")
    public ResponseEntity<MappingParams> multipleObjectPaths(MappingParams paths) {
        log(paths.toString());
        return ResponseEntity.ok(paths);
    }
    
    private void log(String message) {
        log.debug("path :" + message);
    }
    @Getter
    @Setter
    @ToString
    public static class MappingParams {
        private String id;
        private String name;
    }
}
