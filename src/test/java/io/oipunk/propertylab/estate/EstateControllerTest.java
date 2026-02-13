package io.oipunk.propertylab.estate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class EstateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnChineseMessageByDefault() throws Exception {
        mockMvc.perform(get("/api/v1/estates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("楼盘列表查询成功。"));
    }

    @Test
    void shouldReturnEnglishMessageWhenAcceptLanguageIsEnglish() throws Exception {
        mockMvc.perform(get("/api/v1/estates").header("Accept-Language", "en-US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Estate list loaded successfully."));
    }

    @Test
    void shouldReturnLocalizedValidationError() throws Exception {
        mockMvc.perform(post("/api/v1/estates")
                        .header("Accept-Language", "zh-CN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"\",\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("参数校验失败"));
    }

    @Test
    void shouldCreateUpdateAndDeleteEstate() throws Exception {
        String createBody = """
                {
                  "code": "ESTATE-99",
                  "name": "社区99",
                  "address": "测试地址",
                  "remark": "测试备注"
                }
                """;

        mockMvc.perform(post("/api/v1/estates")
                        .header("Accept-Language", "zh-CN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.code").value("ESTATE-99"));

        mockMvc.perform(get("/api/v1/estates/3")
                        .header("Accept-Language", "zh-CN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.code").value("ESTATE-99"));

        String updateBody = """
                {
                  "name": "社区99-更新",
                  "address": "新地址",
                  "remark": "新备注"
                }
                """;
        mockMvc.perform(put("/api/v1/estates/3")
                        .header("Accept-Language", "zh-CN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("社区99-更新"));

        mockMvc.perform(delete("/api/v1/estates/3")
                        .header("Accept-Language", "zh-CN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("楼盘删除成功（ID=3）。"));
    }

    @Test
    void shouldReturnNotFoundWhenEstateMissing() throws Exception {
        mockMvc.perform(get("/api/v1/estates/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("请求失败"));
    }
}
