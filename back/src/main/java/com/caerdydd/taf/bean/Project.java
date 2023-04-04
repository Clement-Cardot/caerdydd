package com.caerdydd.taf.bean;


public class Project {
    int idProjet;
    String name;
    String description;
    boolean isValidated;
    int idJury;

    public Project() {
    }

    public Project(int idProjet, String name, String description, boolean isValidated) {
        this.idProjet = idProjet;
        this.name = name;
        this.description = description;
        this.isValidated = isValidated;
    }

    public Project(int idProjet, String name, String description, boolean isValidated, int idJury) {
        this.idProjet = idProjet;
        this.name = name;
        this.description = description;
        this.isValidated = isValidated;
        this.idJury = idJury;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }

}
