package com.engagement.engagementexercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import com.engagement.engagementexercise.record.EngagementUpdate;
import com.engagement.engagementexercise.repository.EngagementUpdateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EngagementUpdateServiceTest {

    private static final Instant NOW = Instant.parse("2026-10-12T10:00:00Z");
    private static final String PENDING_STATUS = "PENDING";

    // Stub of the repository: like the real query, it returns pending rows for exactly the requested firm.
    private final EngagementUpdateRepository repository = Mockito.mock(EngagementUpdateRepository.class);

    private final EngagementUpdateService service = new EngagementUpdateService(repository);

    private static EngagementUpdate pending(String id, String firmId, String engagementId, int from, int to) {
        return new EngagementUpdate(
                id, firmId, engagementId, from, to, PENDING_STATUS, 0,
                "s3://diffs/T/%d-%d".formatted(from, to), NOW, null, null);
    }

    @Test
    void returnsTheFirmsPendingUpdatesInRepositoryOrder() {
        EngagementUpdate first = pending("u1", "firmA", "e1", 1, 3);
        EngagementUpdate second = pending("u2", "firmA", "e2", 2, 3);
        when(repository.getByFirmIdAndStatus("firmA", PENDING_STATUS)).thenReturn(List.of(first, second));

        List<EngagementUpdate> result = service.listPendingEngagementUpdates("firmA");

        assertEquals(List.of(first, second), result);
    }

    @Test
    void returnsAnEmptyListWhenTheFirmHasNothingPending() {
        List<EngagementUpdate> result = service.listPendingEngagementUpdates("firmA");

        assertTrue(result.isEmpty());
    }

    @Test
    void doesNotReturnAnotherFirmsUpdates() {
        EngagementUpdate firmA = pending("u1", "firmA", "e1", 1, 2);
        EngagementUpdate firmB = pending("u2", "firmB", "e9", 1, 2);
        when(repository.getByFirmIdAndStatus("firmA", PENDING_STATUS)).thenReturn(List.of(firmA));
        when(repository.getByFirmIdAndStatus("firmB", PENDING_STATUS)).thenReturn(List.of(firmB));

        List<EngagementUpdate> result = service.listPendingEngagementUpdates("firmA");

        assertEquals(List.of(firmA), result);
        assertTrue(result.stream().allMatch(u -> u.getFirmId().equals("firmA")));
    }
}