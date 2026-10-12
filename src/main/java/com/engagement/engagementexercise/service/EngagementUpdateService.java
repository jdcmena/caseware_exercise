package com.engagement.engagementexercise.service;

import com.engagement.engagementexercise.record.EngagementUpdate;
import com.engagement.engagementexercise.repository.EngagementUpdateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngagementUpdateService {

    private static final String PENDING_STATUS = "PENDING";

    private final EngagementUpdateRepository engagementUpdateRepository;

    public EngagementUpdateService(EngagementUpdateRepository engagementUpdateRepository) {
        this.engagementUpdateRepository = engagementUpdateRepository;
    }

    public List<EngagementUpdate> listPendingEngagementUpdates(String firmId) {
        return engagementUpdateRepository.getByFirmIdAndStatus(firmId, PENDING_STATUS);
    }

}
