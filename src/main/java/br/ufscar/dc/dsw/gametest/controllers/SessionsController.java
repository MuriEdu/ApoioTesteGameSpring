package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import br.ufscar.dc.dsw.gametest.enums.SessionState;
import br.ufscar.dc.dsw.gametest.repositories.ProjectRepository;
import br.ufscar.dc.dsw.gametest.repositories.SessionRepository;
import br.ufscar.dc.dsw.gametest.repositories.StrategyRepository;
import br.ufscar.dc.dsw.gametest.utils.MockedSessionDependencies;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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



    @GetMapping("/sessions/{id}/status")
    public ResponseEntity<?> getSessionStatus(@PathVariable Long id) {
        Optional<SessionsEntity> optSession = sessionRepository.findById(id);
        if (optSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SessionsEntity session = optSession.get();
        SessionState status = session.getStatus();

        long remainingSeconds = 0;

        if (status == SessionState.IN_PROGRESS && session.getStarted_at() != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startedAt = session.getStarted_at();
            long elapsedSeconds = Duration.between(startedAt, now).getSeconds();
            long totalSeconds = session.getTime_minutes() * 60;

            remainingSeconds = totalSeconds - elapsedSeconds;
            if (remainingSeconds < 0) remainingSeconds = 0;
        }

        return ResponseEntity.ok(Map.of(
                "status", status.name(),
                "remainingSeconds", remainingSeconds
        ));
    }

    @PostMapping("/sessions/{id}/status")
    public ResponseEntity<?> updateSessionStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Optional<SessionsEntity> optSession = sessionRepository.findById(id);
        if (optSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SessionsEntity session = optSession.get();

        String newStatusStr = payload.get("status");
        if (newStatusStr == null) {
            return ResponseEntity.badRequest().body("Missing status");
        }

        SessionState newStatus;
        try {
            newStatus = SessionState.valueOf(newStatusStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value");
        }

        // Set started_at when going to IN_PROGRESS if not already set
        if (newStatus == SessionState.IN_PROGRESS && session.getStarted_at() == null) {
            session.setStarted_at(LocalDateTime.now());
            session.setEnded_at(null);
        }

        // Set ended_at when session finishes
        if (newStatus == SessionState.FINISHED && session.getEnded_at() == null) {
            session.setEnded_at(LocalDateTime.now());
        }

        session.setStatus(newStatus);
        sessionRepository.save(session);

        return ResponseEntity.ok(Map.of("message", "Status updated"));
    }

}
