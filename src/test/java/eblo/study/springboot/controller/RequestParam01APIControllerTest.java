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

import eblo.study.springboot.controller.RequestParam01APIController.RequestParams01;

@WebMvcTest(controllers = RequestParam01APIController.class)
class RequestParam01APIControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    /**
     * @RequestParam(required = false) 테스트, 값 없는 경우  
     * @throws Exception
     */
    @Test
    void requiredfalse() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/request/requiredfalse")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "");
    }

    /**
     * @RequestParam(required = false) 테스트, 값 있는 경우  
     * @throws Exception
     */
    @Test
    void requiredfalseWithId() throws Exception {
        String id = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/request/requiredfalse")
                .param("id", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, id);
    }
    
    /**
     * @RequestParam Optional<String> id 테스트, 값 없는 경우 
     * @throws Exception
     */
    @Test
    void optional() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/request/optional")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "none");
    }
    
    /**
     * @RequestParam Optional<String> id 테스트, 값 있는 경우 
     * @throws Exception
     */
    @Test
    void optionalWithId() throws Exception {
        String id = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/request/optional")
                .param("id", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, id);
    }
    
    /**
     * @RequestParam(defaultValue = "none") 테스트, 값 없는 경우 
     * @throws Exception
     */
    @Test
    void defaultValue() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/request/default")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "none");
    }
    
    /**
     * @RequestParam(defaultValue = "none") 테스트, 값 있는 경우 
     * @throws Exception
     */
    @Test
    void defaultValueWithId() throws Exception {
        String id = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/controller/request/default")
                .param("id", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, id);
    }

    /**
     * date type 테스트, 요청레벨에서 convert 한다.  
     * @throws Exception
     */
    @Test
    void dateMapping() throws Exception {
        String param = "2022-05-12";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/date")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "2022-05-12");
    }

    /**
     * Long type 예제 
     * @throws Exception
     */
    @Test
    void longMapping() throws Exception {
        String param = "12,000.12";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/long")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "12000");
    }

    /**
     * Double type 예제 
     * @throws Exception
     */
    @Test
    void doubleMapping() throws Exception {
        String param = "12,000.12";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/double")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "12000.12");
    }
    
    /**
     * Boolean 테스트 
     * @throws Exception
     */
    @Test
    void booleanMapping() throws Exception {
        String param = "on";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/boolean")
                .param("param", param)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, "true");
    }
    
    /**
     * 객체 매핑 테스트   
     * @throws Exception
     */
    @Test
    void objectMapping() throws Exception {
        String created = "2022-05-12";
        Date createdDt = new SimpleDateFormat("yyyy-MM-dd").parse(created); 
        RequestParams01 params = RequestParams01.builder().id("test").name("테스트").created(createdDt).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/params")
                .param("id", params.getId())
                .param("name", params.getName())
                .param("created", created)
                .param("test", "yes")
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
        RequestParams01 params = RequestParams01.builder().id("test").name("테스트").created(createdDt).build();
        
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(params);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/body")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
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
        String paramId = "paramId";
        String created = "2022-05-12";
        Date createdDt = new SimpleDateFormat("yyyy-MM-dd").parse(created); 
        RequestParams01 params = RequestParams01.builder().id("test").name("테스트").created(createdDt).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/params/"+params.getId())
                .param("id", paramId)
                .param("name", params.getName())
                .param("created", created)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName()))
                .andExpect(jsonPath("$.id").value(paramId));
    }

    /**
     * @RequestBody 테스트    
     * @throws Exception
     */
    @Test
    void bodyMappingWithId() throws Exception {
        String created = "2022-05-12";
        Date createdDt = new SimpleDateFormat("yyyy-MM-dd").parse(created); 
        RequestParams01 params = RequestParams01.builder().id("testId").name("테스트").created(createdDt).build();
        
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(params);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/body/pathId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName()))
                .andExpect(jsonPath("$.id").value(params.getId()));
    }


}
