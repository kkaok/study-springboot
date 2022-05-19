package eblo.study.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import eblo.study.springboot.controller.RequestParam03APIController.RequestParams03;
import eblo.study.springboot.web.servlet.support.DateUtil;

@SpringBootTest
@AutoConfigureMockMvc
class RequestParam03APIControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    private RequestParams03 getRequestParams03() {
        return new RequestParams03("test", " 테스트 ", DateUtil.parseDate("2022-05-12"));
    }

    @Test
    void dateMapping() throws Exception {
        RequestParams03 param = getRequestParams03();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/config/date")
                .param("param", DateUtil.formatDate(param.getCreated()))
                .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(DateUtil.formatDate(param.getCreated(), "yyyy/MM/dd"));
    }

    @Test
    void doubleMapping() throws Exception {
        String param = "12,000.12";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/config/double")
                .param("param", param)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("12000.12");
    }

    @Test
    void objectMapping() throws Exception {
        RequestParams03 params = getRequestParams03();
        mockMvc.perform(MockMvcRequestBuilders.post("/controller/request/config/params")
                .param("id", params.getId())
                .param("name", params.getName())
                .param("test", "yes")
                .param("created", DateUtil.formatDate(params.getCreated()))
                .param("dbl", "12,000.12")
                .param("lng", "12,000.12")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.getName().trim()))
                .andExpect(jsonPath("$.id").value(params.getId()))
                .andExpect(jsonPath("$.dbl").value(12000.12))
                .andExpect(jsonPath("$.lng").value(12000))
                ;
    }


}
