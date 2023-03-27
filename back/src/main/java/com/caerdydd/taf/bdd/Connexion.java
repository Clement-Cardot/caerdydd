package com.caerdydd.taf.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

  static Connection connex = null;
  public static void main(String[] args) {
    try {
      Class.forName("org.mariadb.jdbc.Driver");
      System.out.println("Success 1");
    } catch(ClassNotFoundException e) {
    e.printStackTrace();
    System.out.println("Error 1");
    }
    
    try {
      connex = DriverManager.getConnection("jdbc:mariadb://localhost:3300/ProjetGL", "root", "network");
      System.out.println("Success 2");
    } catch (SQLException e){
      e.printStackTrace();
      System.out.println("Error 2");
    }
  }
}
