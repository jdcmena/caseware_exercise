package com.engagement.engagementexercise;

import com.engagement.engagementexercise.record.EngagementFile;
import com.engagement.engagementexercise.record.EngagementUpdate;
import com.engagement.engagementexercise.repository.EngagementRepository;
import com.engagement.engagementexercise.repository.EngagementUpdateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class EngagementExerciseApplicationIntegTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EngagementUpdateRepository engagementUpdateRepository;

    @Autowired
    private EngagementRepository engagementRepository;

    @BeforeEach
    void cleanup() {
        engagementUpdateRepository.deleteAll();
        engagementRepository.deleteAll();
    }

    void setup() {
        EngagementFile engagementFile = engagementFile("1", "testA","template1",1, true);
        EngagementUpdate engagementUpdate_1 = engagementUpdate("001", "123", "1", 1, 2, "ACCEPTED", 2);
        EngagementUpdate engagementUpdate_2 = engagementUpdate("002", "123", "1", 2, 3, "PENDING", 1);
        EngagementUpdate engagementUpdate_3 = engagementUpdate("003", "122", "1", 2, 3, "PENDING", 1);


        engagementRepository.save(engagementFile);
        engagementUpdateRepository.save(engagementUpdate_1);
        engagementUpdateRepository.save(engagementUpdate_2);
        engagementUpdateRepository.save(engagementUpdate_3);
    }

    @Test
    void getUsedEngagementFiles() throws Exception {
        setup();
        ResultActions result = mvc.perform(get("/engagement/v1/updates")
                .param("firmId", "123")).andDo(print());
        result.andExpect(status().isOk());
        List<EngagementUpdate> response = getResponseObjectList(result.andReturn().getResponse().getContentAsString(), EngagementUpdate.class);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("123", response.getFirst().getFirmId());
    }

    private <T> List<T> getResponseObjectList(String payload, Class<T> clazz) {
        return objectMapper.readValue(payload, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clazz));
    }

    private static EngagementFile engagementFile(
            String id, String firmId, String productTemplateId, int productTemplateVersion, boolean active) {
        return EngagementFile.builder()
                .id(id)
                .firmId(firmId)
                .templateId(productTemplateId)
                .templateVersion(productTemplateVersion)
                .s3Uri("uri")
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .active(active)
                .build();
    }

    private static EngagementUpdate engagementUpdate(
            String id, String firmId, String engagementId, int updateFrom, int updateTo, String status, int revision) {
        return EngagementUpdate.builder()
                .id(id)
                .firmId(firmId)
                .engagementId(engagementId)
                .updateFrom(updateFrom)
                .updateTo(updateTo)
                .status(status)
                .revision(revision)
                .s3DiffUri("s3diffuri")
                .createdAt(Instant.now())
                .decidedAt(null)
                .decidedBy(null)
                .build();
    }

}
