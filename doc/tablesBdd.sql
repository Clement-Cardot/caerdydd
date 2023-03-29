USE ProjetGL;


CREATE TABLE USER (
    id_user INT AUTO_INCREMENT,
    name VARCHAR(20),
    surname VARCHAR(20),
    login VARCHAR(8) UNIQUE,
    password VARCHAR(8),
    CONSTRAINT password CHECK (password LIKE '%[0-9]%' AND password LIKE '%[^a-zA-Z0-9]%' AND LENGTH(password) >= 8),
    email VARCHAR(30),
    role VARCHAR(20),
    PRIMARY KEY(id_user)
);


CREATE TABLE TEACHING_STAFF (
    id_user INT(8),
    speciality VARCHAR(20),
    is_option_leader BOOLEAN,
    is_subject_validator BOOLEAN,
    PRIMARY KEY(id_user),
    FOREIGN KEY (id_user) REFERENCES USER (id_user)
);
CREATE TABLE JURY (
    id_jury INT AUTO_INCREMENT,
    id_ts1 INT(8),
    id_ts2 INT(8),
    PRIMARY KEY(id_jury),
    FOREIGN KEY (id_ts1) REFERENCES TEACHING_STAFF (id_ts),
    FOREIGN KEY (id_ts2) REFERENCES TEACHING_STAFF (id_ts)
);

CREATE TABLE PROJECT (
    id_project  INT AUTO_INCREMENT, 
    name VARCHAR(20),
    description VARCHAR(250),
    is_validated BOOLEAN,
    id_jury INT(8),
    PRIMARY KEY(id_project),
    FOREIGN KEY (id_jury) REFERENCES JURY (id_jury)
);

CREATE TABLE PRESENTATION (
    id_presentation INT AUTO_INCREMENT, 
    type VARCHAR(20),
    datetime_begin DATE,
    datetime_end DATE,
    room VARCHAR(20),
    jury_a_notes INT(8),
    jury_b_notes INT(8),
    id_jury INT(8),
    id_project INT(8),
    PRIMARY KEY(id_presentation),
    FOREIGN KEY (id_jury) REFERENCES JURY (id_jury),
    FOREIGN KEY (id_project) REFERENCES PROJECT (id_project)
    
);


 
CREATE TABLE TEAM (
    id_team  INT AUTO_INCREMENT, 
    name VARCHAR(30),
    team_work_mark INT,
    team_validation_mark INT,
    test_book_link VARCHAR(100),
    file_path_scope_statement VARCHAR(100),
    file_path_final_scope_statement VARCHAR(100),
    file_path_scope_statement_analysis VARCHAR(100),
    file_path_repor VARCHAR(100),
    PRIMARY KEY(id_team),
    id_project_dev INT(8),
    id_project_validation INT(8),
    FOREIGN KEY (id_project_dev) REFERENCES PROJECT (id_project),
    FOREIGN KEY (id_project_validation) REFERENCES PROJECT (id_project)
);

CREATE TABLE CONSULTING (
    id_consulting INT AUTO_INCREMENT,
    datetime_begin DATE,
    datetime_end DATE,
    speciality VARCHAR(20),
    notes VARCHAR(250),
    is_validated BOOLEAN,
    is_reserved BOOLEAN,
    id_team INT(8),
    PRIMARY KEY(id_consulting),
    FOREIGN KEY (id_team) REFERENCES TEAM (id_team)
);



CREATE TABLE TEAM_MEMBER(
    id_tea INT  AUTO_INCREMENT,
    speciality VARCHAR(20),
    individual_mark INT,
    bonus_penalty INT,
    PRIMARY KEY(id_student),
    id_team INT(8),
    id_user INT(8),
    FOREIGN KEY (id_team) REFERENCES TEAM (id_team),
    FOREIGN KEY (id_user) REFERENCES USER (id_user)
);



CREATE TABLE NOTIFICATION (
    id_notification INT AUTO_INCREMENT,
    message VARCHAR(250),
    link VARCHAR(100), 
    id_user INT(8),
    PRIMARY KEY(id_notification),
    FOREIGN KEY (id_user) REFERENCES USER (id_user)
);




CREATE TABLE ASSIGNMENT (
    id_ts INT(8),
    id_consulting INT(8),
    FOREIGN KEY (id_ts) REFERENCES TEACHING_STAFF (id_ts),
    FOREIGN KEY (id_consulting) REFERENCES CONSULTING (id_consulting),
    PRIMARY KEY(id_ts, id_consulting)
);
