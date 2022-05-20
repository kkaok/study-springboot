package eblo.study.springboot.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProfileTestAPIController {

    @Value("${app.title}")
    private String appTitle;
    
    @GetMapping("/profile")
    public String test() {
        log.trace("trace : "+appTitle);
        log.debug("debug : "+appTitle);
        log.info("info : "+appTitle);
        log.error("error : "+appTitle);
        return appTitle;
    }
    
}
