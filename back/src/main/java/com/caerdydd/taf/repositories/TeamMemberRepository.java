package com.caerdydd.taf.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.TeamMemberEntity;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Integer>{
    
}
