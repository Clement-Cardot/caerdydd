package com.caerdydd.taf.dao;

import java.util.List;

import com.caerdydd.taf.bean.TeachingStaff;
import com.caerdydd.taf.bean.TeamMember;
import com.caerdydd.taf.bean.User;

public interface UserDao {
    List<User> getAllUsers();
    List<TeamMember> getAllTeamMembers();
    List<TeachingStaff> getAllTeachingStaff();

    User getUserById(int id);
}
