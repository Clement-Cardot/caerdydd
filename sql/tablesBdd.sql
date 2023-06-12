DROP DATABASE ProjetGL;
CREATE DATABASE ProjetGL;

USE ProjetGL;

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(20) NOT NULL,
    lastname VARCHAR(20) NOT NULL,
    login VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL,
    email VARCHAR(40) NOT NULL,
    speciality ENUM('LD', 'CSS'),
    enabled BOOLEAN NOT NULL DEFAULT 1,
    PRIMARY KEY(id)
);

CREATE TABLE role (
    id_role INT NOT NULL AUTO_INCREMENT,
    id_user INT NOT NULL,
    role ENUM('STUDENT_ROLE', 'TEAM_MEMBER_ROLE', 'PLANNING_ROLE', 'TEACHING_STAFF_ROLE', 'OPTION_LEADER_ROLE', 'JURY_MEMBER_ROLE') NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user (id),
    PRIMARY KEY(id_role)
);

CREATE TABLE teaching_staff (
    id_user INT NOT NULL,
    is_infrastructure_specialist BOOLEAN NOT NULL,
    is_development_specialist BOOLEAN NOT NULL,
    is_modeling_specialist BOOLEAN NOT NULL,
    is_option_leader BOOLEAN NOT NULL,
    is_subject_validator BOOLEAN NOT NULL,
    PRIMARY KEY(id_user),
    FOREIGN KEY (id_user) REFERENCES user (id)
);

CREATE TABLE jury (
    id_jury INT NOT NULL AUTO_INCREMENT,
    id_ts1 INT NOT NULL,
    id_ts2 INT NOT NULL,
    PRIMARY KEY(id_jury),
    FOREIGN KEY (id_ts1) REFERENCES teaching_staff (id_user),
    FOREIGN KEY (id_ts2) REFERENCES teaching_staff (id_user)
);

CREATE TABLE project (
    id_project INT NOT NULL AUTO_INCREMENT, 
    name VARCHAR(20) NOT NULL,
    description VARCHAR(1000),
    is_validated BOOLEAN NOT NULL,
    id_jury INT,
    PRIMARY KEY(id_project),
    FOREIGN KEY (id_jury) REFERENCES jury (id_jury)
);

CREATE TABLE presentation (
    id_presentation INT NOT NULL AUTO_INCREMENT, 
    type ENUM('Présentation intermédiaire', 'Présentation finale','Audit CSS','Audit LD'),
    datetime_begin DATETIME NOT NULL,
    datetime_end DATETIME NOT NULL,
    room VARCHAR(20) NOT NULL,
    jury1_notes VARCHAR(250),
    jury2_notes VARCHAR(250),
    id_jury INT NOT NULL,
    id_project INT NOT NULL,
    PRIMARY KEY(id_presentation),
    FOREIGN KEY (id_jury) REFERENCES jury (id_jury),
    FOREIGN KEY (id_project) REFERENCES project (id_project)
);

CREATE TABLE team (
    id_team INT NOT NULL AUTO_INCREMENT, 
    name VARCHAR(30),
    team_work_mark INT,
    team_validation_mark INT,
    test_book_link VARCHAR(100),
    file_path_scope_statement VARCHAR(100),
    file_path_final_scope_statement VARCHAR(100),
    file_path_scope_statement_analysis VARCHAR(100),
    file_path_report VARCHAR(100),
    is_report_annotation BOOLEAN,
    report_comments VARCHAR(4000),
    id_project_dev INT NOT NULL,
    id_project_validation INT NOT NULL,
    PRIMARY KEY(id_team),
    FOREIGN KEY (id_project_dev) REFERENCES project (id_project),
    FOREIGN KEY (id_project_validation) REFERENCES project (id_project)
);

CREATE TABLE planned_timing_consulting (
    id_planned_timing_consulting INT NOT NULL AUTO_INCREMENT,
    datetime_begin DATETIME NOT NULL,
    datetime_end DATETIME NOT NULL,
    PRIMARY KEY(id_planned_timing_consulting)
);

CREATE TABLE planned_timing_availability (
    id_planned_timing_availability INT NOT NULL AUTO_INCREMENT,
    id_planned_timing_consulting INT NOT NULL,
    id_ts INT NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT 1,
    PRIMARY KEY(id_planned_timing_availability),
    FOREIGN KEY (id_planned_timing_consulting) REFERENCES planned_timing_consulting (id_planned_timing_consulting),
    FOREIGN KEY (id_ts) REFERENCES teaching_staff (id_user)
);


CREATE TABLE consulting (
    id_consulting INT NOT NULL AUTO_INCREMENT,
    speciality ENUM('Infrastructure', 'Développement', 'Modélisation'),
    notes VARCHAR(250),
    id_team INT NOT NULL,
    id_planned_timing_availability INT,
    id_planned_timing_consulting INT NOT NULL,
    PRIMARY KEY(id_consulting),
    FOREIGN KEY (id_team) REFERENCES team (id_team),
    FOREIGN KEY (id_planned_timing_availability) REFERENCES planned_timing_availability (id_planned_timing_availability),
    FOREIGN KEY (id_planned_timing_consulting) REFERENCES planned_timing_consulting (id_planned_timing_consulting)
);



CREATE TABLE team_member (
    id_user INT NOT NULL,
    individual_mark INT,
    bonus_penalty INT,
    id_team INT NOT NULL,
    PRIMARY KEY(id_user),
    FOREIGN KEY (id_team) REFERENCES team (id_team),
    FOREIGN KEY (id_user) REFERENCES user (id)
);

CREATE TABLE notification (
    id_notification INT NOT NULL AUTO_INCREMENT,
    message VARCHAR(250) NOT NULL,
    link VARCHAR(100), 
    id_user INT NOT NULL,
    is_read BOOLEAN NOT NULL,
    PRIMARY KEY(id_notification),
    FOREIGN KEY (id_user) REFERENCES user (id)
);
