INSERT INTO user (firstname, lastname, login, password, email, speciality) VALUES
('Sophie', 'Rousseau' , 'rousseso', '$2a$12$t97oDbasmThFawvcaQkZduFrMmfEwZjB8QiWbEOJSwZoY/Hv/Xx7y', 'sophie.rousseau@eseo.fr', NULL),
('Fabien', 'Chhel', 'chhelfa', ' $2a$12$RvdQtUaAefpCHgqBYeyb1uYuBOuJu4XM73ftSihwpssrxsA.jV0bu ', 'fabien.chhel@eseo.fr', NULL),
('Maissa', 'Abdallah', 'abdallma', '$2a$12$vGzIw1ycJIjk9AMwl20eo.WI7ugT3/unMoBttXwpec.g05P9NcD9C', 'maissa.abdallah@eseo.fr', NULL),
('Olivier', 'Camp', 'campol', '$2a$12$4WdyrZysmPn2a9njpTVh.eNoth4ACC.LzH8CgkQSpAyW7SOTlUZ46', 'olivier.camp@eseo.fr', NULL),
('Mickael', 'Clavreul', 'clavremi', '$2a$12$N7y2.IoA5BrvyYBdZCnhhecw3SUD8erxqdkYCFZQIlJygFXjBX8zW', 'mickael.clavreul@eseo.fr', NULL),
('Clive', 'Ferret-Canape', 'ferretcl', '$2a$12$pnCUeNqsAGMbGRofcX7BVO6iXB7h4PvrEQbUjINRrCOcmnNBGMI6u', 'clive.ferret-canape@eseo.fr', NULL),
('Richard', 'Woodward', 'woodwari', '$2a$12$X3GFVXF93Tw4PJMPzTn0WOu22WpJ.E1J8SFTXhNbgdh0airosZAkq', 'richard.woodward@eseo.fr', NULL),
('Jerome', 'Chavin', 'chavinje', '$2a$12$.5zlxyqsJGSwNjXQ/QucR.niSv1ubQBLv5EaHyHjs9MjTAUMdiQum', 'jerome.chavin@reseau.eseo.fr', NULL),
('Francois', 'Jamet', 'jametfr', '$2a$12$YmratvygaJgmnxxIg/gXQOay6tlEUZa1XqwUmJ2wu7GWrFBb.USma', 'francois.jamet@reseau.eseo.fr', NULL),
('Alexandra', 'Bouvier', 'bouvieal', '$2a$12$G7pnCaJ7xxim5Vw3N/bbEOMJqvjgxyuKx1g1N0nS47cYXRgCM1j4a', 'alexandra.bouvier@reseau.eseo.fr', NULL);

INSERT INTO role (id_user, role) VALUES
(1, 'TEACHING_STAFF_ROLE'),
(2, 'TEACHING_STAFF_ROLE'),
(3, 'TEACHING_STAFF_ROLE'),
(4, 'TEACHING_STAFF_ROLE'),
(5, 'TEACHING_STAFF_ROLE'),
(6, 'TEACHING_STAFF_ROLE'),
(7, 'TEACHING_STAFF_ROLE'),
(8, 'TEACHING_STAFF_ROLE'),
(9, 'TEACHING_STAFF_ROLE'),
(1, 'OPTION_LEADER_ROLE'),
(8, 'OPTION_LEADER_ROLE'),
(10, 'PLANNING_ROLE');

INSERT INTO teaching_staff (id_user, is_infrastructure_specialist, is_development_specialist, is_modeling_specialist, is_option_leader, is_subject_validator) VALUES
(1, 0, 1, 1, 1, 1),
(2, 0, 1, 1, 0, 0),
(3, 0, 1, 1, 0, 0),
(4, 0, 1, 1, 0, 0),
(5, 0, 1, 1, 0, 0),
(6, 0, 1, 1, 0, 0),
(7, 0, 1, 1, 0, 0),
(8, 1, 0, 0, 1, 1),
(9, 1, 0, 0, 0, 0);