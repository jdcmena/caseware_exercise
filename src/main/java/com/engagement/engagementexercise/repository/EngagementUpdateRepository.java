package com.engagement.engagementexercise.repository;

import com.engagement.engagementexercise.record.EngagementUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementUpdateRepository extends JpaRepository<EngagementUpdate, String> {

    List<EngagementUpdate> getByFirmIdAndStatus(String firmId, String status);
}
