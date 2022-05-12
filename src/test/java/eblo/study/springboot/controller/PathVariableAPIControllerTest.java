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

    @Test
    void requiredfalse() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/path/requiredfalse")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println("content : "+content);
        Assertions.assertEquals(content, "");
    }

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
