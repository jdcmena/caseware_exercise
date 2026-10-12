CREATE TABLE engagement_file (
                                 id VARCHAR(255) PRIMARY KEY,
                                 product_template_id VARCHAR(255),
                                 product_template_version INTEGER,
                                 firm_id VARCHAR(255),
                                 active BOOLEAN,
                                 s3_uri VARCHAR(255),
                                 create_date TIMESTAMP,
                                 update_date TIMESTAMP
);
CREATE INDEX idx_engagement_file_firm_template_version ON engagement_file (firm_id, product_template_id, product_template_version);
CREATE INDEX idx_engagement_file_product_template ON engagement_file (product_template_id);

CREATE TABLE engagement_update (
                                   id VARCHAR(255) PRIMARY KEY,
                                   engagement_id VARCHAR(255),
                                   firm_id VARCHAR(255),
                                   version_update_from INTEGER,
                                   version_update_to INTEGER,
                                   status VARCHAR(255),
                                   revision INTEGER,
                                   create_date TIMESTAMP,
                                   decided_at TIMESTAMP,
                                   decided_by VARCHAR(255),
                                   s3_diff_uri VARCHAR(255),
                                   CONSTRAINT fk_engagement FOREIGN KEY (engagement_id) REFERENCES engagement_file(id)

);
CREATE INDEX idx_engagement_update_firm_id_status ON engagement_update (firm_id, status);
