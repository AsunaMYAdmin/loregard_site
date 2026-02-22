package me.asunamyadmin.loregardsite.global.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String mainPage(){
        return "index";
    }

    @GetMapping("/bank")
    public String bankPage(){
        return "redirect:BANK_URL";
    }
}
