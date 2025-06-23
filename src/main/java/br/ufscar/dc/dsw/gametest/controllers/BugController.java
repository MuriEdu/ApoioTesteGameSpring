package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.BugEntity;
import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import br.ufscar.dc.dsw.gametest.repositories.BugRepository;
import br.ufscar.dc.dsw.gametest.repositories.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sessions/{sessionId}/bugs")
public class BugController {

    private final SessionRepository sessionRepository;
    private final BugRepository bugRepository;

    public BugController(SessionRepository sessionRepository, BugRepository bugRepository) {
        this.sessionRepository = sessionRepository;
        this.bugRepository = bugRepository;
    }

    @GetMapping
    public String listSessionBugs(@PathVariable Long sessionId, Model model) {
        SessionsEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + sessionId));

        List<BugEntity> bugs = bugRepository.findBySessionId(session.getId());

        model.addAttribute("sessionId", session.getId());
        model.addAttribute("bugs", bugs);
        return "bugs/list";
    }

    @GetMapping("/new")
    public String showCreateForm(@PathVariable Long sessionId, Model model) {
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + sessionId));

        model.addAttribute("bug", new BugEntity());
        model.addAttribute("sessionId", sessionId);
        return "bugs/form";
    }

    @GetMapping("/{bugId}/edit")
    public String showEditForm(@PathVariable Long sessionId, @PathVariable Long bugId, Model model) {
        BugEntity bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug não encontrado: " + bugId));

        model.addAttribute("bug", bug);
        model.addAttribute("sessionId", sessionId);
        return "bugs/form";
    }

    @PostMapping("/save")
    public String saveOrUpdateBug(@PathVariable Long sessionId,
                                  @ModelAttribute("bug") BugEntity bug,
                                  RedirectAttributes redirectAttributes) {

        if (bug.getId() == 0) {
            SessionsEntity session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + sessionId));
            bug.setSession(session);
            bugRepository.save(bug);
            redirectAttributes.addFlashAttribute("message", "Bug registrado com sucesso!");
        } else {
            BugEntity existingBug = bugRepository.findById(bug.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Bug não encontrado: " + bug.getId()));
            existingBug.setDescription(bug.getDescription());
            bugRepository.save(existingBug);
            redirectAttributes.addFlashAttribute("message", "Bug atualizado com sucesso!");
        }

        return "redirect:/sessions/" + sessionId + "/bugs";
    }

    @PostMapping("/{bugId}/delete")
    public String deleteBug(@PathVariable Long sessionId, @PathVariable Long bugId) {
        bugRepository.deleteById(bugId);
        return "redirect:/sessions/" + sessionId + "/bugs";
    }
}