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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eblo.study.springboot.controller.RequestParam01APIController.RequestParams01;
import eblo.study.springboot.web.servlet.support.DateUtil;

@WebMvcTest(controllers = RequestParam01APIController.class)
class RequestParam01APIControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    private RequestParams01 getRequestParams01() {
        return new RequestParams01("test", "테스트", true, DateUtil.parseDate("2022-05-12"));
    }

    /**
     * @RequestParam 테스트  
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
        "/controller/request/requiredfalse,,", "/controller/request/requiredfalse,test,test"
        ,"/controller/request/optional,,", "/controller/request/optional,test,test"
        ,"/controller/request/default,,none", "/controller/request/default,test,test"
        })
    void requiredfalse(String uri, String id, String expected) throws Exception {
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON);
        if(id != null) mrbuilder.param("id", id);
        if(expected == null) expected = "";
        MvcResult result = mockMvc.perform(mrbuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    /**
     * 타입 매핑 테스트  
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource(value = {
        "/controller/request/date#2022-05-12#2022-05-12"
        ,"/controller/request/long#12,000.12#12000"
        ,"/controller/request/double#12,000.12#12000.12"
        ,"/controller/request/boolean#on#true"
        }, delimiterString="#")
    void typeMapping(String uri, String param, String expected) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
        RequestParams01 params = getRequestParams01();
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.post("/controller/request/params").accept(MediaType.APPLICATION_JSON)
                .param("id", params.getId())
                .param("name", params.getName())
                .param("created", DateUtil.formatDate(params.getCreated()))
                .param("test", (params.getTest())? "yes":"no")
                .contentType(MediaType.APPLICATION_JSON);
        execute(mrbuilder, params);
    }
    
    /**
     * @RequestBody 테스트    
     * @throws Exception
     */
    @Test
    void bodyMapping() throws Exception {
        RequestParams01 params = getRequestParams01();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(params);
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.post("/controller/request/body")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        execute(mrbuilder, params);
    }

    private void execute(MockHttpServletRequestBuilder mrbuilder, RequestParams01 params) throws Exception {
        mockMvc.perform(mrbuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName()))
                .andExpect(jsonPath("$.id").value(params.getId()));
    }

    /**
     * Path와 Parameter에 동일 필드를 이용하는 경우 우선 순위 테스트     
     * @throws Exception
     */
    @Test
    void requestParamAndPath() throws Exception {
        RequestParams01 params = getRequestParams01();
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.post("/controller/request/params/pathId").accept(MediaType.APPLICATION_JSON)
                .param("id", params.getId())
                .param("name", params.getName())
                .param("created", DateUtil.formatDate(params.getCreated()))
                .param("test", (params.getTest())? "yes":"no")
                .contentType(MediaType.APPLICATION_JSON);
        execute(mrbuilder, params);
    }

}
