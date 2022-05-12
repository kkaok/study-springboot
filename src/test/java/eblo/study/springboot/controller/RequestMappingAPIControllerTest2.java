package eblo.study.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RequestMappingAPIController.class)
//@ActiveProfiles("local")
class RequestMappingAPIControllerTest2 {

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
    
//    @Test
//    public void shouldReturnErrorMessageToAdminWhenCreatingUserWithUsedUserName() throws Exception {
//        mockMvc.perform(post("/api/users").header("Authorization", base64ForTestUser).contentType(MediaType.APPLICATION_JSON)
//            .content("{\"userName\":\"testUserDetails\",\"firstName\":\"xxx\",\"lastName\":\"xxx\",\"password\":\"xxx\"}"))
//            .andDo(print())
//            .andExpect(status().isBadRequest())
//            .andExpect(?);
//    }
}
