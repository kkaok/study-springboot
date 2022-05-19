package eblo.study.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

@WebMvcTest(RequestMappingAPIController.class)
class RequestMappingAPIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    private static final String ROOT_URI = "/controller/mapping";
    
    /**
     * @RequestMapping 테스트, get/post/put/delete로 요청을 해본다. 
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
        ROOT_URI+"/requestMapping,requestMapping"
        , ROOT_URI+",getMapping"
        , ROOT_URI+"/test1,getMapping-test1"
        , ROOT_URI+"/test2,getMapping-test2"
        , ROOT_URI+"/test-mapping,getMapping-test2"
        })
    void requestMapping(String uri, String expected) throws Exception {
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON);
        execute(mrbuilder, expected);
    }

    private void execute(MockHttpServletRequestBuilder mrbuilder, String expected) throws Exception {
        MvcResult result = mockMvc.perform(mrbuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expected);
    }
    
    
    /**
     * @PostMapping 테스트 
     * @throws Exception
     */
    @Test
    void postMapping() throws Exception {
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.post(ROOT_URI).contentType(MediaType.APPLICATION_JSON);
        execute(mrbuilder, "postMapping");
    }
    
    /**
     * @PutMapping 테스트 
     * @throws Exception
     */
    @Test
    void putMapping() throws Exception {
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.put(ROOT_URI).contentType(MediaType.APPLICATION_JSON);
        execute(mrbuilder, "putMapping");
    }
    
    /**
     * @DeleteMapping test
     * @throws Exception
     */
    @Test
    void deleteMapping() throws Exception {
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.delete(ROOT_URI).contentType(MediaType.APPLICATION_JSON);
        execute(mrbuilder, "deleteMapping");
    }

}
