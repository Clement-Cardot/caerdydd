package com.caerdydd.taf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.caerdydd.taf.models.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>{
    
    @Query("SELECT new Team(t.idTeam, t.name) FROM Team t")
    List<Team> getAllTeamsNamesAndIDs();
}
