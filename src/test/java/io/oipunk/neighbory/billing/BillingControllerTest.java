package io.oipunk.neighbory.billing;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldListAndCreateAndPayCharge() throws Exception {
        mockMvc.perform(get("/api/v1/billing/charges"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Billing charges loaded successfully."));

        String payload = """
                {
                  "estateCode": "estate-11",
                  "unitCode": "b1-u9",
                  "amount": 98.20,
                  "dueDate": "2026-03-20",
                  "remark": "test billing"
                }
                """;
        String response = mockMvc.perform(post("/api/v1/billing/charges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.paid").value(false))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number idValue = JsonPath.read(response, "$.data.id");
        long id = idValue.longValue();

        mockMvc.perform(patch("/api/v1/billing/charges/{id}/pay", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.paid").value(true));

        mockMvc.perform(get("/api/v1/billing/charges").param("paid", "true"))
                .andExpect(status().isOk());
    }
}
