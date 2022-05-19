package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eblo.study.springboot.controller.ResponseJsonAPIController.JsonTest;
import eblo.study.springboot.web.servlet.support.DateUtil;

@WebMvcTest(controllers = ResponseJsonAPIController.class)
class ResponseJsonAPIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
 
    private JsonTest getJsonTest() {
        return new JsonTest("test", "테스트", true, DateUtil.parseDate("2022-05-12"));
    }

    @Test
    void dateMapping() throws Exception {
        JsonTest params = getJsonTest();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(params);
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/response/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testId").value(params.getId()))
                .andExpect(jsonPath("$.created").value(DateUtil.formatDate(params.getCreated())))
                ;
    }

}
