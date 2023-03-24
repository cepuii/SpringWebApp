package edu.cepuii.controllers;

import edu.cepuii.dao.PersonDao;
import edu.cepuii.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonDao personDao;

    public AdminController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("person")Person person){
        model.addAttribute("people", personDao.getAll());
        return "admin/admin";
    }

    @PatchMapping("/add")
    public String addAdmin(@ModelAttribute("person") Person person){
        System.out.println(person.getId());
        return "redirect:/people";
    }

}
