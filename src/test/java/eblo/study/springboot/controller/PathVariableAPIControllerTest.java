package eblo.study.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PathVariableAPIController.class)
class PathVariableAPIControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    private static final String ROOT_URI = "/controller/path";

    /**
     * 매핑 테스트 
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
        ROOT_URI+"/test,test"
        , ROOT_URI+"/requiredfalse/test,test"
        , ROOT_URI+"/requiredfalse,"
        , ROOT_URI+"/optional/test,test"
        , ROOT_URI+"/optional,none"
        })
    void simpleMapping(String uri, String expected) throws Exception {
        MockHttpServletRequestBuilder mrbuilder = MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON);
        if(expected == null) expected = "";
        MvcResult result = mockMvc.perform(mrbuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(expected);
    }

    /**
     * multiple path 테스트   
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
        ROOT_URI+"/test/eblo,test,eblo"
        , ROOT_URI+"/map/test/eblo,test,eblo"
        , ROOT_URI+"/object/test/eblo,test,eblo"
        })
    void multiplePaths(String uri, String id, String name) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id));
    }

}
