package eblo.study.springboot.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/controller/request/initbinder")
public class RequestParam02APIController {

    /**
     * class level convert : class 전체 적용 
     * @param webDataBinder
     * @param request
     */
    @InitBinder
    private void initBinder(WebDataBinder webDataBinder, WebRequest request) {
        // datetime format 설정 
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetimeFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Timestamp.class, new CustomDateEditor(datetimeFormat, true));
        // date format 설정 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        // trim 설정 
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        // number 관련 설정 
        NumberStyleFormatter numberFormatter = new NumberStyleFormatter();
        numberFormatter.setPattern("#,###,###,###.##");
        webDataBinder.addCustomFormatter(numberFormatter);
    }

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
    public ResponseEntity<RequestParams02> requestParamObject(final RequestParams02 params) {
        log.debug("requestParamDate call : "+params.toString());
        return ResponseEntity.ok(params);
    }
    
    /**
     * request parameters를 객체에서 받고 date 타입은 formate 지정을 한다.   
     * request level에서 convert 처리 
     * @return
     */
    @PostMapping("/body")
    public ResponseEntity<RequestParams02> requestParamBody(@RequestBody final RequestParams02 params) {
        log.debug("requestParamBody call : "+params.toString());
        return ResponseEntity.ok(params);
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestParams02 {
        private String id;
        private String name;
        private Boolean test;
        private Date created;
    }
}
