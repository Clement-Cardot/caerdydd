package com.caerdydd.taf.dao;

import java.util.List;

import com.caerdydd.taf.bean.User;

public interface UserDao {
    List<User> getAllUsers();
    User getUserById(int id);
}
