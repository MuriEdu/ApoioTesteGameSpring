package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import br.ufscar.dc.dsw.gametest.repositories.SessionRepository;
import br.ufscar.dc.dsw.gametest.utils.MockedSessionDependencies;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SessionsController {

    private final SessionRepository sessionRepository;
    private final MockedSessionDependencies mockedSessionDependencies;

    public SessionsController(SessionRepository sessionRepository, MockedSessionDependencies mockedSessionDependencies) {
        this.sessionRepository = sessionRepository;
        this.mockedSessionDependencies = mockedSessionDependencies;
    }

    @GetMapping("/sessions")
    public String listSessions(Model model, Authentication authentication) {

        List<SessionsEntity> sessions = sessionRepository.findAll();
        model.addAttribute("sessions", sessions);

        return "sessions";
    }

    @GetMapping("/sessions/new")
    public String newSession(Model model, Authentication authentication) {
        List<StrategyEntity> strategies = mockedSessionDependencies.getStrategies();
    }

}
