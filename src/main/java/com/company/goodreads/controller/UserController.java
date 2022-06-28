package com.company.goodreads.controller;

import com.company.goodreads.dao.entity.UsersEntity;
import com.company.goodreads.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String register(Model model) { //fronta bos user obyekti gonderirik,htmlde doldurulacaq bu obyekt
        model.addAttribute("user", new UsersEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") UsersEntity user, BindingResult bindingResult) { //fronta yuxarida gondeiyim bos user model obyektini doldurulmus sekilde aliriq.
        //TODO: Check fName lName email validations useeri bos gonderende xeta atir
        UsersEntity existingUser = userService.getUserByLogin(user.getLogin());
        if (existingUser != null) {
            bindingResult.rejectValue("login", "error.user", "User already exist");
        }
        if (bindingResult.hasErrors()) { // eger error bas verse validation check zamani , onda exception atmiyib, hemen errorlari bindingResult obyektine doldurulur.Ve bizde Controllerde mueyyen addimlar goruruk
            return "registration";
        }
        userService.register(user);  //@valid  UserEnttiy-e yazdigimiz validationlarin yoxlanilmasi ucun istifade olunur.gelen obyekt yolxanilacaq ,her sey duzgun olsa bazaya add olunacaq
        return "redirect:/users/login"; // redirect edirik login sehifesine.
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
