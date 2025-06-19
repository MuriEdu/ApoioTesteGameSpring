package br.ufscar.dc.dsw.gametest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.gametest.entities.GenreEntity;
import br.ufscar.dc.dsw.gametest.repositories.GameRepository;
import br.ufscar.dc.dsw.gametest.repositories.GenreRepository;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public String listGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "genre/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("genre", new GenreEntity());
        return "genre/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        GenreEntity genre = genreRepository.findById(id).orElseThrow();
        model.addAttribute("genre", genre);
        return "genre/form";
    }

    @PostMapping("/save")
    public String saveGenre(@ModelAttribute("genre") GenreEntity genre) {
        genreRepository.save(genre);
        return "redirect:/genres";
    }

    @PostMapping("/{id}/delete")
    public String deleteGenre(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isAssociated = gameRepository.findAll().stream()
            .anyMatch(game -> game.getGenres() != null && game.getGenres().stream().anyMatch(g -> g.getId().equals(id)));
        if (isAssociated) {
            redirectAttributes.addFlashAttribute("deleteError", "Não é possível deletar: gênero está associado a um ou mais jogos.");
            return "redirect:/genres";
        }
        genreRepository.deleteById(id);
        return "redirect:/genres";
    }
}