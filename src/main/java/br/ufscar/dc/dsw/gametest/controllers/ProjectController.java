package br.ufscar.dc.dsw.gametest.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.repositories.GameRepository;
import br.ufscar.dc.dsw.gametest.repositories.ProjectRepository;
import br.ufscar.dc.dsw.gametest.repositories.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;
    private final GameRepository gameRepository;

    public ProjectController(ProjectRepository projectRepo, UserRepository userRepo, GameRepository gameRepository) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public String list(Model model, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<ProjectEntity> projects;

        if (isAdmin) {
            projects = projectRepo.findAll();
        } else {
            // Tester só vê projetos dos quais faz parte
            var userEmail = authentication.getName(); // o e-mail é o username
            var user = userRepo.findByEmail(userEmail).orElse(null);
            projects = (user != null) ? projectRepo.findByMemberId(user.getId()) : List.of();
        }
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/new")
    public String newProject(Model model) {
        model.addAttribute("project", new ProjectEntity());
        model.addAttribute("allUsers", userRepo.findAll());
        model.addAttribute("allGames", gameRepository.findAll());
        return "project/form";
    }

    @GetMapping("/{id}/edit")
    public String editProject(@PathVariable int id, Model model) {
        ProjectEntity project = projectRepo.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("project", project);
        model.addAttribute("allUsers", userRepo.findAll());
        model.addAttribute("allGames", gameRepository.findAll());
        return "project/form";
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute("project") @Valid ProjectEntity project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allUsers", userRepo.findAll());
            return "project/form";
        }

        projectRepo.save(project);
        return "redirect:/projects";
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectRepo.deleteById(id);
        return "redirect:/projects";
    }
}
