package com.engagement.engagementexercise.repository;

import com.engagement.engagementexercise.record.EngagementFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngagementRepository extends JpaRepository<EngagementFile, String> {
}
