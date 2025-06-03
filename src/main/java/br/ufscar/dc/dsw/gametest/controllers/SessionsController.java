package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import br.ufscar.dc.dsw.gametest.enums.SessionState;
import br.ufscar.dc.dsw.gametest.repositories.ProjectRepository;
import br.ufscar.dc.dsw.gametest.repositories.SessionRepository;
import br.ufscar.dc.dsw.gametest.repositories.StrategyRepository;
import br.ufscar.dc.dsw.gametest.utils.MockedSessionDependencies;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SessionsController {

    private final SessionRepository sessionRepository;
    private final ProjectRepository projectRepository;
    private final StrategyRepository strategyRepository;

    public SessionsController(SessionRepository sessionRepository, MockedSessionDependencies mockedSessionDependencies, ProjectRepository projectRepository, StrategyRepository strategyRepository) {
        this.sessionRepository = sessionRepository;

        this.projectRepository = projectRepository;
        this.strategyRepository = strategyRepository;
    }

    @GetMapping("/sessions")
    public String listSessions(Model model, Authentication authentication) {

        List<SessionsEntity> sessions = sessionRepository.findAll();
        model.addAttribute("testSessions", sessions);

        return "sessions";
    }

    @GetMapping("/sessions/new")
    public String newSession(Model model) {
        List<StrategyEntity> strategies = strategyRepository.findAll();
        List<ProjectEntity> projects = projectRepository.findAll();

        model.addAttribute("strategies", strategies);
        model.addAttribute("projects", projects);

        // Bind empty form
        model.addAttribute("sessionForm", new SessionsEntity());

        return "protected/create-session";
    }


    @PostMapping("/sessions")
    public String createSession(@RequestParam("project_id") Long projectId,
                                @RequestParam("strategy_id") Long strategyId,
                                @RequestParam("duration") int duration) {

        ProjectEntity project = projectRepository.findById(projectId).get();
        StrategyEntity strategy = strategyRepository.findById(strategyId).get();


        // Build final session object
        SessionsEntity session = new SessionsEntity();
        session.setProject(project);
        session.setStrategy(strategy);
        session.setTime_minutes(duration);
        session.setStatus(SessionState.CREATED);

        sessionRepository.save(session);
        return "redirect:/sessions";
    }

}
