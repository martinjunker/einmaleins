CREATE TABLE USERS
(
    USERNAME VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(68) NOT NULL,
    ENABLED  TINYINT(1)  NOT NULL,
    PRIMARY KEY (USERNAME)
);
CREATE TABLE AUTHORITIES
(
    USERNAME  VARCHAR(50) NOT NULL,
    AUTHORITY VARCHAR(68) NOT NULL,
    FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME)
);

// add admin with key "pass" to start with
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('Admin', '$2a$10$cRqfrdolNVFW6sAju0eNEOE0VC29aIyXwfsEsY2Fz2axy3MnH8ZGa', 1);

INSERT INTO AUTHORITIES
VALUES ('Admin', 'ROLE_ADMIN');
