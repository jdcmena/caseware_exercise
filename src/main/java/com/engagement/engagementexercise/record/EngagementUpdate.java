package com.engagement.engagementexercise.record;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable snapshot of an engagement_update row. State changes produce a new instance with an
 * incremented revision, which the repository uses for compare-and-set writes.
 *
 * Invariants enforced here: updateTo > updateFrom; a PENDING row has no decision fields; every
 * closed row (applied, declined, superseded) records when and by whom it was closed.
 */
@Entity
@Table(name = "engagement_update")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EngagementUpdate {

    private @Id String id;
    private String firmId;
    private String engagementId;
    private int updateFrom;
    private int updateTo;
    private String status;
    private int revision;
    private String s3DiffUri;
    private Instant createdAt;
    private Instant decidedAt;
    private String decidedBy;

}