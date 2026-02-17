package io.oipunk.neighbory.estate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class EstateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnListMessage() throws Exception {
        mockMvc.perform(get("/api/v1/estates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Estate list loaded successfully."));
    }

    @Test
    void shouldReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/v1/estates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"\",\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"));
    }

    @Test
    void shouldCreateUpdateAndDeleteEstate() throws Exception {
        String createBody = """
                {
                  "code": "ESTATE-99",
                  "name": "Estate 99",
                  "address": "Test Avenue",
                  "remark": "Test remark"
                }
                """;

        mockMvc.perform(post("/api/v1/estates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.code").value("ESTATE-99"));

        mockMvc.perform(get("/api/v1/estates/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.code").value("ESTATE-99"));

        String updateBody = """
                {
                  "name": "Estate 99 Updated",
                  "address": "New Avenue",
                  "remark": "New remark"
                }
                """;
        mockMvc.perform(put("/api/v1/estates/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Estate 99 Updated"));

        mockMvc.perform(delete("/api/v1/estates/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Estate deleted successfully (id=3)."));
    }

    @Test
    void shouldReturnNotFoundWhenEstateMissing() throws Exception {
        mockMvc.perform(get("/api/v1/estates/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Request failed"));
    }
}
