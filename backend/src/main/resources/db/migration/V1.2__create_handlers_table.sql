CREATE TABLE PUBLIC.HANDLERS(
    ID          SERIAL          PRIMARY KEY,
    NAME        VARCHAR(32)     NOT NULL,
    TOPIC       VARCHAR(256)    NOT NULL
);

-- Insert values to have it available for local testing
INSERT INTO PUBLIC.HANDLERS(NAME, TOPIC)
    VALUES
        ('Filter users', 'flow-manager.filter'),
        ('Send Email', 'flow-manager.send-email');