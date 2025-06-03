package br.ufscar.dc.dsw.gametest.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "/index";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        boolean isAdmin = false;

        if (isAuthenticated) {
            isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);

        return "home"; // Thymeleaf template name (dashboard.html)
    }
}
