package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PathVariableAPIController.class)
class PathVariableAPIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    /**
     * 단순 매핑 테스트 
     * @throws Exception
     */
    @Test
    void simpleMapping() throws Exception {
        String id = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, id);
    }

    /**
     * id를 필수가 아닌걸로 설정하고 id 없이 보내는 테스트. 
     * @throws Exception
     */
    @Test
    void requiredfalse() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/requiredfalse")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "");
    }

    /**
     * id를 필수가 아닌걸로 설정하고 id에 값을 넣고 요청.  
     * @throws Exception
     */
    @Test
    void requiredfalseId() throws Exception {
        String id = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/requiredfalse/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, id);
    }

    /**
     * path optional 설정 테스트 
     * @throws Exception
     */
    @Test
    void optionalId() throws Exception {
        String id = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/optional/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, id);
    }

    /**
     * path optional 설정하고 값이 없는 경우 "none"을 반환  
     * @throws Exception
     */
    @Test
    void optionalIdNone() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/optional")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "none");
    }

    /**
     * multiple path 테스트   
     * @throws Exception
     */
    @Test
    void multiplePaths() throws Exception {
        String id = "test";
        String name = "eblo";
        mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/"+id+"/"+name)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id));
    }

    /**
     * path 정보를 Map으로 받는 테스트 
     * @throws Exception
     */
    @Test
    void multipleMapPaths() throws Exception {
        String id = "test";
        String name = "eblo";
        mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/map/"+id+"/"+name)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.id").value(id));
    }
    
    /**
     * path 정보를 객체로로 받는 테스트 
     * @throws Exception
     */
    @Test
    void multipleObjectPaths() throws Exception {
        String id = "test";
        String name = "eblo";
        mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/object/"+id+"/"+name)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.id").value(id));
    }
    
}
