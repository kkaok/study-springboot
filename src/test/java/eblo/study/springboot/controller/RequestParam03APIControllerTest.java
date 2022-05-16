package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import eblo.study.springboot.controller.RequestParam03APIController.RequestParams03;

@SpringBootTest
@AutoConfigureMockMvc
class RequestParam03APIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    @Test
    void dateMapping() throws Exception {
        String param = "2022-05-12";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/config/date")
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
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/config/double")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(Double.valueOf(content), resultValue);
    }

    @Test
    void objectMapping() throws Exception {
        String created = "2022-05-12";
        Date createdDt = new SimpleDateFormat("yyyy-MM-dd").parse(created); 
        RequestParams03 params = RequestParams03.builder().id("test").name(" 테스트 ").created(createdDt).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/config/params")
                .param("id", params.getId())
                .param("name", params.getName())
                .param("test", "yes")
                .param("created", created)
                .param("dbl", "12,000.12")
                .param("lng", "12,000.12")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName().trim()))
                .andExpect(jsonPath("$.id").value(params.getId()))
                .andExpect(jsonPath("$.dbl").value(12000.12))
                .andExpect(jsonPath("$.lng").value(12000))
                ;
    }


}
