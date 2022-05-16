package eblo.study.springboot.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/request/config")
public class RequestParam03APIController {

    /**
     * date 타입 받기 yyyy-MM-dd 
     * @return
     */
    @PostMapping("/date")
    public ResponseEntity<String> dateMapping(@RequestParam final Date param) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  
        String strDate = dateFormat.format(param);  
        log.debug("strDate : "+strDate);
        return ResponseEntity.ok(strDate);
    }

    /**
     * 12,000.12 형태로 전달 받으면 12000.12로 변환 
     * @param pDouble
     * @return
     */
    @PostMapping("/double")
    public ResponseEntity<Double> doubleMapping(@RequestParam final Double param) {
        log.debug("parameter : "+param);
        return ResponseEntity.ok(param);
    }

    /**
     * 12,000.12 형태로 전달 받으면 12000로 변환 
     * @param pLong
     * @return
     */
    @PostMapping("/long")
    public ResponseEntity<Long> longMapping(@RequestParam final Long param) {
        log.debug("parameter : "+param);
        return ResponseEntity.ok(param);
    }

    /**
     * trim 처리 
     * @param pString
     * @return
     */
    @PostMapping("/trim")
    public ResponseEntity<String> trimMapping(@RequestParam final String param) {
        log.debug("parameter : "+param);
        return ResponseEntity.ok(param);
    }
    
    @PostMapping("/params")
    public ResponseEntity<RequestParams03> requestParamObject(final RequestParams03 params) {
        log.debug("requestParamDate call : "+params.toString());
        return ResponseEntity.ok(params);
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestParams03 {
        private String id;
        private String name;
        private Boolean test;
        private Date created;
        private Double dbl;
        private Long lng;
    }
}
