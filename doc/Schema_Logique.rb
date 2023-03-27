@startuml


entity STUDENT {
    + id_student [PK]
    --
    speciality
    individual_mark
    bonus_penalty
    #id_team [FK]
    #id_user [FK]
}

entity USER {
    + id_user [PK]
    --
    name
    surname
    login
    password
    email
    type
}


entity PRESENTATION {
    + id_presentation [PK]
    --
    type
    datetime_begin
    datetime_end
    room
    jury_a_notes
    jury_b_notes
    # id_jury [FK]
    # id_projet [FK]
}

entity PROJECT {
    + id_project [PK]
    --
    name
    description
    is_validated
    #id_jury[FK]
}

entity TEAM {
    + id_team [PK]
    --
    name
    team_work_mark
    team_validation_mark
    test_book_link
    file_path_scope_statement
    file_path_final_scope_statement
    file_path_scope_statement_analysis
    file_path_report
    #id_project_dev [FK]
    #id_project_validation [FK]
}

entity CONSULTING {
    + id_consulting [PK]
    --
    datetime_begin
    datetime_end
    speciality
    notes
    is_validated
    is_reserved
    #id_team [FK]
}

entity NOTIFICATION {
    + id_notification [PK]
    --
    message
    link
    #id_user [FK]
}

entity JURY {
    + id_jury [PK]
    --
    # id_ts1 [FK]
    # id_ts2 [FK]
}

entity TEACHING_STAFF {
    + id_ts [PK]
    --
    speciality
    is_option_leader
    is_jury_member
    is_subject_validator
    #id_user [FK]
}

entity ASSIGNMENT {
    - id_ts [PK] [FK]
    - id_consulting [PK] [FK]
  }

PRESENTATION -right-> JURY
JURY-down->TEACHING_STAFF
ASSIGNMENT -down->TEACHING_STAFF
ASSIGNMENT -left->CONSULTING
PROJECT -down->JURY
PRESENTATION -right-> PROJECT
TEAM -left->PROJECT
TEAM -left->PROJECT
CONSULTING -up-> TEAM
STUDENT-left-> TEAM
STUDENT -right-> USER
NOTIFICATION -down-> USER
TEACHING_STAFF -up-> USER
@enduml
