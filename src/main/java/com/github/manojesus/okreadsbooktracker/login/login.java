package com.github.manojesus.okreadsbooktracker.login;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class login {
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/sing-up")
    public String getSingUpPage(){
        return "sing-up";
    }

    @PostMapping("/registeruser")
    public ModelAndView registerUser(@RequestBody MultiValueMap<String,String> userInformation){
        String name = userInformation.getFirst("name");
        String username = userInformation.getFirst("username");
        String email = userInformation.getFirst("email");
        String password = userInformation.getFirst("password");



        return new ModelAndView("redirect:/home");
    }
}
