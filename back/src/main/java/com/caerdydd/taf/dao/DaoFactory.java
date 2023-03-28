package com.caerdydd.taf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DaoFactory {

    public static final ResourceBundle CONFIGURATION = ResourceBundle.getBundle("application");
	    
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }
        
        String ip = CONFIGURATION.getString("SQL_IP");
        String port = CONFIGURATION.getString("SQL_PORT");
        String database = CONFIGURATION.getString("SQL_DATABASE");
        String username = CONFIGURATION.getString("SQL_USERNAME");
        String password = CONFIGURATION.getString("SQL_PASSWORD");
        
        return new DaoFactory("jdbc:mariadb://"+ip+":"+port+"/"+database, username, password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Récupération du Dao
    public UserDao getUserDao() {
        return new UserDaoImpl(this);
    }
}
