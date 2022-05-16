package eblo.study.springboot.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/request")
public class RequestParam01APIController {

    /**
     * id가 필수가 아닌 경우 처리 : required = false 
     * @return
     */
    @GetMapping("/requiredfalse")
    public ResponseEntity<String> requestParamRequiredFalse(@RequestParam(required = false) final String id) {
        log.debug("requestParamRequiredFalse call : "+id);
        return ResponseEntity.ok(id);
    }

    /**
     * id가 필수가 아닌 경우 처리 : optional  
     * @return
     */
    @GetMapping("/optional")
    public ResponseEntity<String> requestParamOptional(@RequestParam final Optional<String> id) {
        log.debug("requestParamOptional call : "+id);
        return ResponseEntity.ok(id.orElse("none"));
    }

    /**
     * id가 필수가 아닌 경우 처리 : defaultValue  
     * @return
     */
    @GetMapping("/default")
    public ResponseEntity<String> requestParamDefault(@RequestParam(defaultValue = "none") final String id) {
        log.debug("requestParamDefault call : "+id);
        return ResponseEntity.ok(id);
    }

    /**
     * date type 형식 지정 형태
     * request level에서 convert 처리 
     * @return
     */
    @PostMapping("/date")
    public ResponseEntity<String> requestParamDate(@RequestParam("param") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(date);  
        log.debug("requestParamDate call : "+strDate);
        return ResponseEntity.ok(strDate);
    }

    @PostMapping("/long")
    public ResponseEntity<Long> requestParamLong(@RequestParam("param") @NumberFormat(pattern = "#,###,###,###.###") final Long pLong) {
        log.debug("requestParamLong call : "+pLong);
        return ResponseEntity.ok(pLong);
    }

    @PostMapping("/double")
    public ResponseEntity<Double> requestParamDouble(@RequestParam("param") @NumberFormat(pattern = "#,###,###,###.###") final Double pDouble) {
        log.debug("requestParamDouble call : "+pDouble);
        return ResponseEntity.ok(pDouble);
    }

    @PostMapping("/boolean")
    public ResponseEntity<Boolean> requestParamBoolean(@RequestParam("param") final Boolean pBoolean) {
        log.debug("requestParamBoolean call : "+pBoolean);
        return ResponseEntity.ok(pBoolean);
    }
    
    /**
     * request parameters를 객체에서 받고 date 타입은 formate 지정을 한다.   
     * request level에서 convert 처리 
     * @return
     */
    @PostMapping("/params")
    public ResponseEntity<RequestParams01> requestParamObject(final RequestParams01 params) {
        log.debug("requestParamDate call : "+params.toString());
        return ResponseEntity.ok(params);
    }
    
    /**
     * request parameters를 객체에서 받고 date 타입은 formate 지정을 한다.   
     * request level에서 convert 처리 
     * @return
     */
    @PostMapping("/body")
    public ResponseEntity<RequestParams01> requestParamBody(@RequestBody final RequestParams01 params) {
        log.debug("requestParamBody call : "+params.toString());
        return ResponseEntity.ok(params);
    }

    /**
     * PathVariable과 @RequestBody 예제 
     * @param params
     * @param path
     * @return
     */
    @PostMapping("/body/{id}")
    public ResponseEntity<RequestParams01> requestParamBodyWithId(@RequestBody final RequestParams01 params, final RequestParams01 path) {
        log.debug("requestParamBodyWithId call : "+params.toString());
        log.debug("requestParamBodyWithId call : "+path.toString());
        return ResponseEntity.ok(params);
    }
    
    /**
     * pathvariable과 request parameters를 객체에 받을 때 같은 이름으로 요청했을 때 parameter 값이 우선한다.    
     * request level에서 convert 처리 
     * @return
     */
    @PostMapping("/params/{id}")
    public ResponseEntity<RequestParams01> requestParamAndPath(final RequestParams01 params) {
        log.debug("requestParamDate call : "+params.toString());
        return ResponseEntity.ok(params);
    }
    
    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestParams01 {
        
        private String id;
        private String name;
        private Boolean test;
        
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date created;
        
        public void setCreated(Long timestamp) {
            if(timestamp == null) return;
            this.created = new Date(timestamp); 
        }
    }

}
