package io.oipunk.neighbory.parking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldListAssignAndRelease() throws Exception {
        mockMvc.perform(get("/api/v1/parking/spaces").header("Accept-Language", "zh-CN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("车位列表查询成功。"));

        mockMvc.perform(patch("/api/v1/parking/spaces/2/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerName\":\"李四\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.occupied").value(true));

        mockMvc.perform(patch("/api/v1/parking/spaces/2/release"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.occupied").value(false));
    }
}
