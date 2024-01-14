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
//    Mapping to user_form screen
    @GetMapping("/users/new")
    public String showNewForm(Model model) {
//        Data to be passed to the model from view
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }
//    Action on clicking save button/submission of the form
    @PostMapping("/user/save")
    public String saveUser(User user, RedirectAttributes ra) {
//        data from view persisted to db
        service.save(user);
//        Feedback message
        ra.addFlashAttribute("message", "The user has been saved successfully.");
//        Redirect to users screen
        return "redirect:/users";
    }
//    Mapping to users_form specific to the user to be edited
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable ("id") Integer id, Model model, RedirectAttributes ra) {
        try {
//            Fetch user "id"
            User user = service.get(id);
//            Pass user "id" to model
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " +id+  ")");
//            Append user "id" to view
            return "user_form";
        }
        catch(UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser (@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
        }
        catch(UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }
}
