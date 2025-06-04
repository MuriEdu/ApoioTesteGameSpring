package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import br.ufscar.dc.dsw.gametest.enums.Roles;
import br.ufscar.dc.dsw.gametest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<UserEntity> users = userRepo.findAll();
        List<UserEntity> admins = users.stream().filter(u -> u.getRole() == Roles.ADMIN).toList();
        List<UserEntity> testers = users.stream().filter(u -> u.getRole() == Roles.TESTER).toList();

        model.addAttribute("admins", admins);
        model.addAttribute("testers", testers);
        return "user/list";
    }


    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("roles", Roles.values());
        return "user/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.values());
        return "user/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") @Valid UserEntity user, BindingResult result, Model model) {

        // Valida e-mail duplicado
        var existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent() && (user.getId() == 0 || existing.get().getId()  != user.getId())) {
            result.rejectValue("email", "error.user", "Este e-mail já está em uso.");
        }

        if (result.hasErrors()) {
            model.addAttribute("roles", Roles.values());
            return "user/form";
        }

        // Lógica de senha
        if (user.getId() != 0 && user.getId() != 0 && (user.getPassword() == null || user.getPassword().isBlank())) {
            String currentPassword = userRepo.findById(user.getId()).map(UserEntity::getPassword).orElse(null);
            user.setPassword(currentPassword);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepo.save(user);
        return "redirect:/users";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}
