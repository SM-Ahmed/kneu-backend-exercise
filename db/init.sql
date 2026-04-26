CREATE TABLE medications (
    id     BIGSERIAL    PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    dosage VARCHAR(255) NOT NULL
);

INSERT INTO medications (id, name, dosage) VALUES
    (1, 'Aspirin',     '100mg'),
    (2, 'Metformin',   '500mg'),
    (3, 'Lisinopril',  '10mg'),
    (4, 'Amoxicillin', '250mg'),
    (5, 'Omeprazole',  '20mg');

ALTER SEQUENCE medications_id_seq RESTART WITH 6;

CREATE TABLE prescriptions (
                             id     BIGSERIAL    PRIMARY KEY,
                             label   VARCHAR(255),
);

CREATE TABLE prescription_medications ( --Assume one prescription may take multiple medicines or that this can happen in future
                               prescription_id   BIGINT NOT NULL,
                               medication_id     BIGINT NOT NULL,
                               administration_time   TIMESTAMP, --Assume this is optional and can be NULL. Assume that it is daily medication: do not need to specify frequency etc (e.g. via cron expression)
                               PRIMARY KEY (prescription_id, medication_id),
                               FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
                               FOREIGN KEY (medication_id) REFERENCES medications(id)
);