package me.asunamyadmin.loregardsite.global.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Value("${bank.url}")
    private String bankURL;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/bank")
    public String bankPage(){
        return "redirect:" + bankURL;
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        model.addAttribute("activePage", "profile");
        return "profile";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/rules")
    public String rulesPage(Model model) {
        model.addAttribute("activePage", "rules");
        return "rules";
    }

    @GetMapping("/heroes")
    public String topPage(Model model) {
        model.addAttribute("activePage", "heroes");
        return "heroes";
    }

    @GetMapping("/guilds")
    public String guildsPage(Model model) {
        model.addAttribute("activePage", "guilds");
        return "guilds";
    }

    @GetMapping("/faq")
    public String faqPage(Model model) {
        model.addAttribute("activePage", "faq");
        return "faq";
    }

    @GetMapping("/contacts")
    public String contactPage(Model model) {
        model.addAttribute("activePage", "contacts");
        return "contacts";
    }

    @GetMapping("/support")
    public String supportPage(Model model) {
        model.addAttribute("activePage", "support");
        return "support";
    }

    @GetMapping("/shop")
    public String shopPage(Model model) {
        model.addAttribute("activePage", "shop");
        return "development";
    }

    @GetMapping("/map")
    public String mapPage(Model model) {
        model.addAttribute("activePage", "map");
        return "development";
    }

    @GetMapping("/wiki")
    public String wikiPage(Model model) {
        model.addAttribute("activePage", "shop");
        return "development";
    }

    @GetMapping("/forum")
    public String forumPage(Model model) {
        model.addAttribute("activePage", "shop");
        return "development";
    }

    @GetMapping("/banlist")
    public String banlistPage(Model model) {
        model.addAttribute("activePage", "banlist");
        return "banlist";
    }

    @GetMapping("/staff")
    public String staffPage(Model model) {
        model.addAttribute("activePage", "staff");
        return "staff";
    }

    @GetMapping("/play")
    public String launcherPage(Model model) {
        model.addAttribute("activePage", "play");
        return "play";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String ignoredError, Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/profile";
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/logout")
    public String logoutPage() {

        return "redirect:/logout";
    }
}
