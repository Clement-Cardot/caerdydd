package com.caerdydd.taf.dao;

import java.util.List;
import com.caerdydd.taf.bean.User;

public class UserDaoImpl implements UserDao{

    private DaoFactory daoFactory;

    public UserDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public List<User> getAllUsers() {
        // TODO
        return null;
    }
    public User getUserById(int id) {
        // TODO
        return null;
    }
}
