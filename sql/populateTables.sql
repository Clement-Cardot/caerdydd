INSERT INTO user (firstname, lastname, login, password, email, speciality) VALUES
('Sophie', 'Rousseau' , 'rousseauso', ' $2a$12$mWCdiM1cjZQdVBfNSlXPi.PaQDGPBdMv93xSTPHm5/9IdIXLgy3lK ', 'sophie.rousseau@eseo.fr', NULL),
('Fabien', 'Chell', 'chellfa', ' $2a$12$wQlnPDk6Fl5ilIJqJAeDpunRwtuTUL2oncjvZ8m9IUNJbjYlkCRgG ', 'fabien.chell@eseo.fr', NULL),
('Maissa', 'Abdallah', 'abdallahma', ' $2a$12$YQCz0lImJuMAtYe3U8U5xeryGId1W3YJBBZAJcAxlyh93D7oiPJpW ', 'maissa.abdallah@eseo.fr', NULL),
('Olivier', 'Camp', 'campol', ' $2a$12$6VO2WseFEPiCwUmPBnLdQeF44W4YCJE5MtiLUxYr06qQerXOTvfmG ', 'olivier.camp@eseo.fr', NULL),
('Mickael', 'Clavreul', 'clavreulmi', ' $2a$12$p/AXv4Ra0FZ9zWMRgeYuqOVMB5Ap8lMH1nInLJseWpvkEJRvfhkUa ', 'mickael.clavreul@eseo.fr', NULL),
('Clive', 'Ferret-Canape', 'ferretcanapecl', ' $2a$12$1d/CyHg4AxyL8LxjkLvH7uSPLmbGWaulF1R.g/FkKRlUb2iNl1YKS ', 'clive.ferret-canape@eseo.fr', NULL),
('Richard', 'Woodward', 'woodwardri', ' $2a$12$oydW.mXoyMMWJ.xCgd9yFeqnm9VQEc/bWAN/nH1sp4B1qHOUyUag2 ', 'richard.woodward@eseo.fr', NULL)

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