package com.mycompany.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

//    Create a reference to UserService
    @Autowired private UserService service;
//    Map to users screen
    @GetMapping("/users")
    public String showUserList(Model model){
//        Get data from UserService
        List<User> listUser = service.listAll();
//        Pass the data to model; the view can access the data for display
        model.addAttribute("listUser", listUser);

        return "users";
    }
    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }
    @PostMapping("/user/save")
    public String saveUser(User user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable ("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            return "user_form";
        }
        catch(UserNotFoundException e) {
                ra.addFlashAttribute("message", "The user has been saved successfully...");
            return "redirect/users";
        }
    }
}
