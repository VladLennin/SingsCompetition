package com.sings.competition.repository;

import com.sings.competition.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionsRepository extends JpaRepository<Competition,Long> {
    List<Competition> findAll();

}
