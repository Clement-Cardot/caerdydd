package com.caerdydd.taf.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.user.TeamMemberEntity;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Integer>{
    
}
