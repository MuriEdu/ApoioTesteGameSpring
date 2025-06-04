package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import br.ufscar.dc.dsw.gametest.enums.Roles;
import br.ufscar.dc.dsw.gametest.repositories.StrategyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/strategies")
public class StrategyController {

    private final StrategyRepository strategyRepository;

    public StrategyController(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    private boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_" + Roles.ADMIN.name()));
    }

    @GetMapping
    public String listStrategies(Model model, Authentication auth) {
        List<StrategyEntity> strategies = strategyRepository.findAll();
        model.addAttribute("strategies", strategies);
        model.addAttribute("isAdmin", isAdmin(auth));
        return "strategy/list";
    }

    @GetMapping("/{id}")
    public String viewStrategy(@PathVariable Long id, Model model, Authentication auth) {
        StrategyEntity strategy = strategyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estratégia não encontrada"));
        model.addAttribute("strategy", strategy);
        model.addAttribute("isAdmin", isAdmin(auth));
        return "strategy/view";
    }

    @GetMapping("/new")
    public String createStrategyForm(Model model, Authentication auth) {
        if (!isAdmin(auth)) return "redirect:/strategies";
        model.addAttribute("strategy", new StrategyEntity());
        return "strategy/form";
    }

    @GetMapping("/{id}/edit")
    public String editStrategy(@PathVariable Long id, Model model, Authentication auth) {
        if (!isAdmin(auth)) return "redirect:/strategies";
        StrategyEntity strategy = strategyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estratégia não encontrada"));
        model.addAttribute("strategy", strategy);
        return "strategy/form";
    }

    @PostMapping
    public String saveOrUpdateStrategy(@ModelAttribute StrategyEntity strategy, Authentication auth) {
        if (!isAdmin(auth)) return "redirect:/strategies";

        strategyRepository.save(strategy); // funciona para criar e editar
        return "redirect:/strategies";
    }


    @PostMapping("/{id}/delete")
    public String deleteStrategy(@PathVariable Long id, Authentication auth) {
        if (!isAdmin(auth)) return "redirect:/strategies";
        strategyRepository.deleteById(id);
        return "redirect:/strategies";
    }
}
