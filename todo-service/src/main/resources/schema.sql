CREATE TABLE todos (
    id              BIGINT      NOT NULL    AUTO_INCREMENT,
    title           VARCHAR(80) NOT NULL,
    description     VARCHAR(500),
    creation_date   DATETIME    NOT NULL,
    due_date        DATETIME,
    is_completed    BIT         NOT NULL,
    PRIMARY KEY (id)
)