package com.engagement.engagementexercise.record;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "engagement_file")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EngagementFile {

    private @Id String id;
    private String firmId;
    private String templateId;
    private int templateVersion;
    private String s3Uri;
    private Instant createDate;
    private Instant updateDate;
    private boolean active;
}
