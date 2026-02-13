package io.oipunk.propertylab.agent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AgentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAgentSteps() throws Exception {
        mockMvc.perform(post("/api/v1/agents/assist")
                        .header("Accept-Language", "zh-CN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"我要缴费账单不对\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("多 Agent 协作建议已生成。"))
                .andExpect(jsonPath("$.data.steps.length()").value(2));
    }

    @Test
    void shouldValidateBlankText() throws Exception {
        mockMvc.perform(post("/api/v1/agents/assist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
