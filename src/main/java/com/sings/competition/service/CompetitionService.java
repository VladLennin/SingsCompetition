package com.sings.competition.service;

import com.sings.competition.domain.Competition;
import com.sings.competition.domain.Competitor;
import com.sings.competition.domain.TypesCompetitors;
import com.sings.competition.domain.User;
import com.sings.competition.repository.CompetitionsRepository;
import com.sings.competition.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionService {

    private final CompetitionsRepository competitionRepo;

    public void createCompetition(Competition competition) throws Exception {
        if (competition.getDate() == null || competition.getName().equals("") || competition.getDescription().equals("")) {
            throw new Exception("Введіть усі поля!");
        }
        competitionRepo.save(competition);
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepo.findAll();
    }

    public Competition getById(long id) throws Exception {
        return competitionRepo.findById(id).orElseThrow(() -> new Exception());
    }

    public void addCompetitor(String name, User user, List<String> members, String type, Long id) throws Exception {
        Competitor competitor = new Competitor(name, user, members, TypesCompetitors.valueOf(type));
        Competition competition = competitionRepo.findById(id).orElseThrow(() -> new Exception());
        competition.getCompetitors().add(competitor);
        competitionRepo.save(competition);

    }


    public List<Competition> getNearestCompetitions(int count) {
        return getAllCompetitions().stream().sorted(Comparator.comparing(Competition::getDate).reversed()).filter(x -> x.isActive()).limit(count).toList();
    }
}
