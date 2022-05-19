package eblo.study.springboot.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileTestAPIController {

    @Value("${app.title}")
    private String appTitle;
    
    @GetMapping("/profile")
    public String test() {
        return appTitle;
    }
    
}
