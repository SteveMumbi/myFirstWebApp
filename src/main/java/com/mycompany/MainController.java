package com.mycompany;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Controls the homePage
@Controller
public class MainController {

    //Handler method for index.html
    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

}
