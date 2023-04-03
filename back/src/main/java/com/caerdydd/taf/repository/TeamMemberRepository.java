package com.caerdydd.taf.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer>{
    
}
