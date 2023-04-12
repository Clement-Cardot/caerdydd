INSERT INTO user (firstname, lastname, login, password, email, speciality) VALUES
('Jean', 'Dupont', 'jdupont', '$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q', 'jean.dupont@reseau.eseo.fr', 'LD'),
('Pierre', 'Durand', 'pdurand', '$2a$12$zeVlwY3Eo6mBVM3INdB4GeXWrk8PpG4/gLs3W7.C9ZLmaLVFx9SOC', 'pierre.durand@reseau.eseo.fr', 'CSS'),
('Paul', 'Martin', 'pmartin', '$2a$12$lHpX7qcs5rf4wH4CejKBKu5j3FXqPwhyHl4Iki4vd3eSYEOfC7rbm', 'paul.martin@reseau.eseo.fr', 'LD'),
('Jacques', 'Petit', 'jpetit', '$2a$12$L4RDLQG0fV7qnp99BBtW/eq1BoI8bVkwe5LchAuPmM76GWDuroDbi', 'jacques.petit@reseau.eseo.fr', 'LD'),
('Sophie', 'Rousseau' , 'srousseau', '$2a$12$fA/Ihe/902MPfd8MZYoqEOXpk7GEw/oI9Gw8msi01OnM0BeCHeiDO', 'sophie.rousseau@eseo.fr', NULL),
('Fabien', 'Chell', 'fchell', '$2a$12$SAU1bTDSVlfa5zfGwF7Ac.Ha98rqyZ3agWSb4qUh0WVMK0J2/Yfoi', 'fabien.chell@eseo.fr', NULL);

INSERT INTO role (id_user, role) VALUES
(1, 'STUDENT_ROLE'),
(2, 'STUDENT_ROLE'),
(3, 'STUDENT_ROLE'),
(4, 'STUDENT_ROLE'),
(5, 'OPTION_LEADER_ROLE'),
(6, 'TEACHING_STAFF_ROLE');

INSERT INTO teaching_staff (id_user, is_infrastructure_specialist, is_development_specialist, is_modeling_specialist, is_option_leader, is_subject_validator) VALUES
(5, 0, 1, 0, 0, 1),
(6, 0, 0, 1, 1, 1);

INSERT INTO project (name, description, is_validated) VALUES
('Projet 1', 'Description du projet', 0),
('Projet 2', 'Description du projet', 0),
('Projet 3', 'Description du projet', 0),
('Projet 4', 'Description du projet', 0),
('Projet 5', 'Description du projet', 0),
('Projet 6', 'Description du projet', 0),
('Projet 7', 'Description du projet', 0),
('Projet 8', 'Description du projet', 0);

INSERT INTO team (name, id_project_dev, id_project_validation) VALUES
('Equipe 1', 1, 2),
('Equipe 2', 2, 1),
('Equipe 3', 3, 4),
('Equipe 4', 4, 3),
('Equipe 5', 5, 6),
('Equipe 6', 6, 5),
('Equipe 7', 7, 8),
('Equipe 8', 8, 7);