-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE election_type (
    id          SERIAL          PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    name        VARCHAR(200)    NOT NULL
);

CREATE TABLE elections (
    id          SERIAL          PRIMARY KEY,
    type        BIGINT          NOT NULL,
    year        INT             DEFAULT DATE_PART('YEAR', NOW()),
    deadline    DATE            DEFAULT CURRENT_DATE + '14 DAYS'::INTERVAL,
    status      VARCHAR(10)     DEFAULT 'U tijeku',
    FOREIGN KEY (type) REFERENCES election_type(id),
    UNIQUE (type, year)
);

CREATE TABLE person (
    id          BIGSERIAL       PRIMARY KEY,
    first_name  VARCHAR(200)    NOT NULL,
    last_name   VARCHAR(200)    NOT NULL,
    dob         DATE            NOT NULL,
    address     VARCHAR(200)    NOT NULL,
    pid         CHAR(11)        UNIQUE NOT NULL,
    nationality VARCHAR(200)    NOT NULL,
    sex         CHAR(1)         NOT NULL
);

CREATE TABLE position (
    id          SERIAL          PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    name        VARCHAR(200)    NOT NULL
);

CREATE TABLE party (
    id          SERIAL          PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    name        VARCHAR(200)    NOT NULL
);

CREATE TABLE candidate (
    id          SERIAL          PRIMARY KEY,
    election    BIGINT          NOT NULL,
    person      BIGINT          NOT NULL,
    position    BIGINT          NOT NULL,
    party       BIGINT,
    applied     DATE            NOT NULL,
    FOREIGN KEY (election) REFERENCES elections(id),
    FOREIGN KEY (person) REFERENCES person(id),
    FOREIGN KEY (position) REFERENCES position(id),
    FOREIGN KEY (party) REFERENCES party(id)
);