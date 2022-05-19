package eblo.study.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eblo.study.springboot.controller.RequestParam02APIController.RequestParams02;
import eblo.study.springboot.web.servlet.support.DateUtil;

@WebMvcTest(controllers = RequestParam02APIController.class)
class RequestParam02APIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
 
    private RequestParams02 getRequestParams02() {
        return new RequestParams02("test", "테스트", true, DateUtil.parseDate("2022-05-12"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/controller/request/initbinder/date#2022-05-12#2022/05/12"
        ,"/controller/request/initbinder/double#12,000.12#12000.12"
        ,"/controller/request/initbinder/long#12,000.12#12000"
        ,"/controller/request/initbinder/trim# 12,000.12 #12,000.12"
        }, delimiterString="#")
    void dateMapping(String uri, String param, String expected) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    /**
     * 객체 매핑 테스트   
     * @throws Exception
     */
    @Test
    void objectMapping() throws Exception {
        RequestParams02 params = getRequestParams02();
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/initbinder/params")
                .param("id", params.getId())
                .param("name", params.getName())
                .param("created", DateUtil.formatDate(params.getCreated()))
                .param("test", (params.getTest())? "yes":"no")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName().trim()))
                .andExpect(jsonPath("$.id").value(params.getId()));
    }
    
    /**
     * @RequestBody 테스트    
     * @throws Exception
     */
    @Test
    void bodyMapping() throws Exception {
        RequestParams02 params = getRequestParams02();
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
