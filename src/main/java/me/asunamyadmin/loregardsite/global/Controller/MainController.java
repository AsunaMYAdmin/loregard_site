package me.asunamyadmin.loregardsite.global.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Value("${bank.url}")
    private String bankURL;



    @GetMapping
    public String mainPage(){
        return "index";
    }

    @GetMapping("/bank")
    public String bankPage(){
        return "redirect:" + bankURL;
    }
}
