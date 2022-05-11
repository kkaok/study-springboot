package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RequestMappingAPIController.class)
class RequestMappingAPIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    @Test
    void requestMapping() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/mapping/requestMapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "requestMapping");
    }
    
    @Test
    void getMapping() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/mapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "getMapping");
    }
    
    @Test
    void postMapping() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/mapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "postMapping");
    }
    
    @Test
    void putMapping() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/controller/mapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "putMapping");
    }
    
    @Test
    void deleteMapping() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/controller/mapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "deleteMapping");
    }

    @Test
    void getMappingTest1() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/mapping/test1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "getMapping-test1");
    }
    
    @Test
    void getMappingTest2() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/mapping/test2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "getMapping-test2");
    }
    
    @Test
    void testMapping() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/mapping//test-mapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "getMapping-test2");
    }
    
}
