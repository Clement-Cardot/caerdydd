package com.caerdydd.taf.bean;

public class TeachingStaff extends User {

    // Beans of TeachingStaff
    private String speciality;
    private boolean isOptionLeader;
    private boolean isSubjectValidator;


    public TeachingStaff(int id, String name, String surname, String login, String password, String email, String speciality, boolean isOptionLeader, boolean isSubjectValidator) {
        super(id, name, surname, login, password, email);
        this.speciality = speciality;
        this.isOptionLeader = isOptionLeader;
        this.isSubjectValidator = isSubjectValidator;
    }

    // Getters and Setters
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public boolean isOptionLeader() {
        return isOptionLeader;
    }

    public void setOptionLeader(boolean optionLeader) {
        isOptionLeader = optionLeader;
    }

    public boolean isSubjectValidator() {
        return isSubjectValidator;
    }

    public void setSubjectValidator(boolean subjectValidator) {
        isSubjectValidator = subjectValidator;
    }

}
