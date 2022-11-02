package com.sings.competition.controller;

import com.sings.competition.domain.User;
import com.sings.competition.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    public String registrationNewUser(@RequestParam("username") String username,
                                      @RequestParam("password1") String password1,
                                      @RequestParam("password2") String password2,
                                      @RequestParam("name") String name,
                                      @RequestParam("surname") String surname,
                                      Model model) {
        User user = new User();
        if(password1.equals(password2)) {
            user = new User(username, password1, name, surname);
        }
        else{
            model.addAttribute("error","Паролі не співпадають");
            return "error";
        }

        try {
            userService.createUser(user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


}
