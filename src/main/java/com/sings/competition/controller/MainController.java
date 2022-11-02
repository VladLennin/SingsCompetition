package com.sings.competition.controller;

import com.sings.competition.domain.Competition;
import com.sings.competition.domain.User;
import com.sings.competition.service.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private final CompetitionService competitionService;


    @GetMapping("/")
    public String getMainPage(Model model) {

        model.addAttribute("nearestCompetitions", competitionService.getNearestCompetitions(5));
        return "main";
    }

    @GetMapping("/profile")
    public String getProfilePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }


}
