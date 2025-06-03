package br.ufscar.dc.dsw.gametest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // refers to login.html in templates/
    }

//    @PostMapping("/login")
//    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
//        // Dummy login logic
//        if ("admin".equals(username) && "1234".equals(password)) {
//            return "redirect:/home";
//        } else {
//            model.addAttribute("error", "Invalid credentials");
//            return "login";
//        }
//    }
}
