package br.ufscar.dc.dsw.gametest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.gametest.entities.GameEntity;
import br.ufscar.dc.dsw.gametest.repositories.GameRepository;
import br.ufscar.dc.dsw.gametest.repositories.GenreRepository;
import br.ufscar.dc.dsw.gametest.repositories.ProjectRepository;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @GetMapping
    public String listGames(@RequestParam(required = false) Long genreId, Model model) {
        if (genreId != null) {
            model.addAttribute("games", gameRepository.findByGenres_Id(genreId));
            model.addAttribute("selectedGenre", genreId);
        } else {
            model.addAttribute("games", gameRepository.findAll());
            model.addAttribute("selectedGenre", null);
        }
        model.addAttribute("genres", genreRepository.findAll());
        return "game/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("game", new GameEntity());
        model.addAttribute("allGenres", genreRepository.findAll());
        return "game/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        GameEntity game = gameRepository.findById(id).orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("allGenres", genreRepository.findAll());
        return "game/form";
    }

    @PostMapping("/save")
    public String saveGame(@ModelAttribute("game") GameEntity game) {
        gameRepository.save(game);
        return "redirect:/games";
    }

    @PostMapping("/{id}/delete")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isAssociated = projectRepository.findAll().stream()
            .anyMatch(project -> project.getGame() != null && project.getGame().getId().equals(id));
        if (isAssociated) {
            redirectAttributes.addFlashAttribute("deleteError", "Não é possível deletar: jogo está associado a um ou mais projetos.");
            return "redirect:/games";
        }
        gameRepository.deleteById(id);
        return "redirect:/games";
    }
}