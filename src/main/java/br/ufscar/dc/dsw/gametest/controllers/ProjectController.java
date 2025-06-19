package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import br.ufscar.dc.dsw.gametest.enums.Roles;
import br.ufscar.dc.dsw.gametest.repositories.ProjectRepository;
import br.ufscar.dc.dsw.gametest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    public ProjectController(ProjectRepository projectRepo, UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String list(Model model) {
        List<ProjectEntity> projects = projectRepo.findAll();
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/new")
    public String newProject(Model model) {
        model.addAttribute("project", new ProjectEntity());
        model.addAttribute("allUsers", userRepo.findAll());
        return "project/form";
    }

    @GetMapping("/{id}/edit")
    public String editProject(@PathVariable int id, Model model) {
        ProjectEntity project = projectRepo.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("project", project);
        model.addAttribute("allUsers", userRepo.findAll());
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
