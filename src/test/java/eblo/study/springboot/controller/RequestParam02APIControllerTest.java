package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eblo.study.springboot.controller.RequestParam02APIController.RequestParams02;

@WebMvcTest(controllers = RequestParam02APIController.class)
class RequestParam02APIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
 
    @Test
    void dateMapping() throws Exception {
        String param = "2022-05-12";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/date")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "2022/05/12");
    }

    @Test
    void doubleMapping() throws Exception {
        String param = "12,000.12";
        Double resultValue = 12000.12d;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/double")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(Double.valueOf(content), resultValue);
    }

    @Test
    void longMapping() throws Exception {
        String param = "12,000.12";
        Long resultValue = 12000l;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/long")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(Long.valueOf(content), resultValue);
    }
    
    @Test
    void trimMapping() throws Exception {
        String param = " 12,000.12 ";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/trim")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, param.trim());
    }

    /**
     * 객체 매핑 테스트   
     * @throws Exception
     */
    @Test
    void objectMapping() throws Exception {
        String created = "2022-05-12";
        Date createdDt = new SimpleDateFormat("yyyy-MM-dd").parse(created); 
        RequestParams02 params = RequestParams02.builder().id("test").name(" 테스트 ").created(createdDt).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/params")
                .param("id", params.getId())
                .param("name", params.getName())
                .param("test", "yes")
                .param("created", created)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName()))
                .andExpect(jsonPath("$.id").value(params.getId()));
    }
    
    /**
     * @RequestBody 테스트    
     * @throws Exception
     */
    @Test
    void bodyMapping() throws Exception {
        String created = "2022-05-12";
        Date createdDt = new SimpleDateFormat("yyyy-MM-dd").parse(created); 
        RequestParams02 params = RequestParams02.builder().id("test").name("테스트").test(true).created(createdDt).build();
        
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(params);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/body")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName()))
                .andExpect(jsonPath("$.id").value(params.getId()));
    }
}
