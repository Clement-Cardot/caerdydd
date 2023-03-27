CREATE TABLE PRESENTAION {
id_presentation INT PRIMARY KEY NOT NULL, 
type VARCHAR,
datetime_begin DATE,
datetime_end DATE,
room VARCHAR,
jury_a_notes INT,
jury_b_notes,
id_jury FOREIGN KEY (id_jury) REFERENCES JURY (id_jury),
id_projet FOREIGN KEY (id_projet) REFERENCES PROJECT (id_projet),
}

CREATE TABLE PROJECT {
id_project  INT PRIMARY KEY NOT NULL, 
name VARCHAR,
description VARCHAR,
is_validated BOOLEAN,
id_jury FOREIGN KEY (id_jury) REFERENCES JURY (id_jury),
}
 
CREATE TABLE TEAM {
id_team  INT PRIMARY KEY NOT NULL, 
name VARCHAR,
team_work_mark INT,
team_validation_mark INT,
test_book_link VARCHAR,
file_path_scope_statement BLOB,
file_path_final_scope_statement BLOB,
file_path_scope_statement_analysis BLOB,
file_path_repor BLOB,
id_project_dev FOREIGN KEY (id_project_dev) REFERENCES PROJET (id_project),
id_project_validation FOREIGN KEY (id_project_validation) REFERENCES PROJET (id_project),
}

CREATE TABLE CONSULTING {
id_consulting INT PRIMARY KEY NOT NULL,
datetime_begin DATE,
datetime_end DATE,
speciality VARCHAR,
notes VARCHAR,
is_validated BOOLEAN,
is_reserved BOOLEAN,
id_team FOREIGN KEY (id_team) REFERENCES TEAM (id_team),
}

CREATE TABLE JURY {
id_jury INT PRIMARY KEY NOT NULL,
id_ts FOREIGN KEY (id_ts) REFERENCES TEACHING_STAFF (id_ts),
}

CREATE TABLE STUDENT{
id_student INT PRIMARY KEY NOT NULL,
speciality VARCHAR,
individual_mark INT,
bonus_penalty INT,
}id_team FOREIGN KEY (id_team) REFERENCES TEAM (id_team),
id_user FOREIGN KEY (id_user) REFERENCES USER (id_user),


CREATE TABLE USER {
id_user INT PRIMARY KEY NOT NULL,
name VARCHAR,
surname VARCHAR,
login VARCHAR UNIQUE,
password VARCHAR,
CONSTRAINT chk_password CHECK (password LIKE '%[0-9]%' AND password LIKE '%[^a-zA-Z0-9]%' AND LENGTH(password) >= 8)
email VARCHAR,
type VARCHAR,
}

CREATE TABLE NOTIFICATION {
id_notification INT PRIMARY KEY NOT NULL,
message VARCHAR,
link VARCHAR, 
id_user FOREIGN KEY (id_user) REFERENCES USER (id_user),
}

CREATE TABLE PLANNING_ASSISTANT [
id_pa INT PRIMARY KEY NOT NULL,
id_user FOREIGN KEY (id_user) REFERENCES USER (id_user),
}

CREATE TABLE TEACHING_STAFF {
id_ts INT PRIMARY KEY NOT NULL,
speciality VARCHAR,
is_option_leader BOOLEAN,
is_jury_member BOOLEAN,
is_subject_validator BOOLEAN,
id_user FOREIGN KEY (id_user) REFERENCES USER (id_user),
}

CREATE TABLE ASSIGNMENT {
id_ts INT PRIMARY KEY NOT NULL,
id_consulting INT PRIMARY KEY NOT NULL,
id_ts FOREIGN KEY (id_ts) REFERENCES TEACHING_STAFF (id_ts),
id_consulting FOREIGN KEY (id_consulting) REFERENCES CONSULTING (id_consulting),
}
