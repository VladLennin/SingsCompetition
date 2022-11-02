package com.sings.competition.controller;


import com.sings.competition.domain.Competition;
import com.sings.competition.domain.Competitor;
import com.sings.competition.domain.TypesCompetitors;
import com.sings.competition.domain.User;
import com.sings.competition.service.CompetitionService;
import com.sings.competition.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final UserService userService;


    @GetMapping("/all-competitions")
    public String getAllCompetitions(Model model) {
        model.addAttribute("competitions", competitionService.getAllCompetitions());
        return "all-competitions";
    }

    @GetMapping("/create-competition")
    public String getCreateCompetitionPage(Model model) {
        return "create-competition";
    }

    @PostMapping("/create-competition")
    public String createNewCompetition(@RequestParam("name") String name, @RequestParam("date") String date, @RequestParam("description") String description, Model model) {
        try {
            competitionService.createCompetition(new Competition(LocalDate.parse(date), name, description));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        model.addAttribute("nearestCompetitions", competitionService.getNearestCompetitions(5));
        return "main";
    }

    @GetMapping("/competition/{id}")
    public String getCompetitionPage(@PathVariable("id") long id, Model model) throws Exception {
        model.addAttribute("competition", competitionService.getById(id));
        return "competition";
    }

    @GetMapping("/reg-on-competition/{id}")
    public String getCompRegPage(@PathVariable("id") long id, Model model, @AuthenticationPrincipal User user) {
        user = userService.getUserByUsername(user.getUsername());
        model.addAttribute("leader", user);
        model.addAttribute("types", TypesCompetitors.values());
        model.addAttribute("id", id);
        return "reg-on-competition";
    }

    @PostMapping("/reg-on-competition/{id}")
    public String regCommand(@RequestParam("members") List<String> members,
                             @RequestParam("name") String name,
                             @PathVariable("id") long id,
                             @RequestParam("type") String type,
                             @AuthenticationPrincipal User user) throws Exception {
        competitionService.addCompetitor(name, user, members, type, id);
        return "redirect:/all-competitions";
    }

    @GetMapping("/competition/{competitionId}/competitor/{competitorId}")
    public String getCompetitorPage(Model model,
                                    @PathVariable("competitionId") long competitionId,
                                    @PathVariable("competitorId") long competitorId) throws Exception {
        Competition competition = competitionService.getById(competitionId);
        Competitor competitor = competition.getCompetitors().stream().filter(competitor1 -> competitor1.getId() == competitorId).findFirst().orElseThrow();
        model.addAttribute("competitor", competitor);
        return "competitor";
    }


//    @ModelAttribute("user")
//    public User asdasd() {
//        return userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//    }

}
