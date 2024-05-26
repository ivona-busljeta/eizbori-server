-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE election_type (
    id          SERIAL          PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    name        VARCHAR(200)    NOT NULL
);

CREATE TABLE election (
    id          BIGSERIAL       PRIMARY KEY,
    type        BIGINT          NOT NULL,
    year        INT             DEFAULT DATE_PART('YEAR', NOW()),
    event_date  DATE            DEFAULT CURRENT_DATE + '90 DAYS'::INTERVAL,
    deadline    DATE            DEFAULT CURRENT_DATE + '14 DAYS'::INTERVAL,
    status      VARCHAR(20)     DEFAULT 'U tijeku',
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
    sex         CHAR(1)         NOT NULL,
    CHECK (pid ~ '[1-9][0-9]{10}')
);

CREATE TABLE position (
    id          BIGSERIAL       PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    name        VARCHAR(200)    NOT NULL
);

CREATE TABLE party (
    id          BIGSERIAL       PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    name        VARCHAR(200)    NOT NULL
);

CREATE TABLE candidate (
    id          BIGSERIAL       PRIMARY KEY,
    election    BIGINT          NOT NULL,
    person      BIGINT          NOT NULL,
    position    BIGINT          NOT NULL,
    party       BIGINT,
    applied     DATE            DEFAULT CURRENT_DATE,
    UNIQUE (election, person),
    FOREIGN KEY (election) REFERENCES election(id),
    FOREIGN KEY (person) REFERENCES person(id),
    FOREIGN KEY (position) REFERENCES position(id),
    FOREIGN KEY (party) REFERENCES party(id)
);