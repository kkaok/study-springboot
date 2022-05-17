package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eblo.study.springboot.controller.ResponseJsonAPIController.JsonTest;

@WebMvcTest(controllers = ResponseJsonAPIController.class)
class ResponseJsonAPIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
 
    @Test
    void dateMapping() throws Exception {
        String created = "2022-05-12";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        Date createdDt = dateFormat.parse(created); 
        String strDate = dateFormat.format(createdDt);
        System.out.println("strDate : "+strDate);

        JsonTest params = new JsonTest("test", "테스트", true, createdDt);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(params);
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/response/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testId").value(params.getId()))
                .andExpect(jsonPath("$.created").value(strDate))
                ;
    }

}
