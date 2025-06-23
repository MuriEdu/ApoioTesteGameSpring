package br.ufscar.dc.dsw.gametest.controllers;

import br.ufscar.dc.dsw.gametest.entities.ProjectEntity;
import br.ufscar.dc.dsw.gametest.entities.SessionsEntity;
import br.ufscar.dc.dsw.gametest.entities.StrategyEntity;
import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import br.ufscar.dc.dsw.gametest.repositories.*;
import br.ufscar.dc.dsw.gametest.enums.SessionState;
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
    private final UserRepository userRepository;

    public SessionsController(
            SessionRepository sessionRepository,
            ProjectRepository projectRepository,
            StrategyRepository strategyRepository,
            UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.projectRepository = projectRepository;
        this.strategyRepository = strategyRepository;
        this.userRepository = userRepository;
    }


    // Exibe o formulário de edição
    @GetMapping("/sessions/{id}/edit")
    public String editSession(@PathVariable Long id, Model model) {
        SessionsEntity session = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + id));

        model.addAttribute("sessionForm", session);
        model.addAttribute("strategies", strategyRepository.findAll());
        model.addAttribute("projects",   projectRepository.findAll());
        return "sessions/session-form";
    }

    // Salva as alterações
    @PostMapping("/sessions/{id}/save")
    public String updateSession(@PathVariable Long id,
                                @RequestParam("project_id")  Long projectId,
                                @RequestParam("strategy_id") Long strategyId,
                                @RequestParam("duration")    int  duration) {

        SessionsEntity session = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + id));

        session.setProject  (projectRepository .findById(projectId) .orElseThrow());
        session.setStrategy (strategyRepository.findById(strategyId).orElseThrow());
        session.setTime_minutes(duration);

        // Se a sessão ainda não foi finalizada, volta ao estado “CREATED” 
        if (session.getStatus() != SessionState.FINISHED) {
            session.setStatus   (SessionState.CREATED);
            session.setStarted_at(null);
            session.setEnded_at  (null);
        }
        sessionRepository.save(session);
        return "redirect:sessions/sessions";
    }

    @GetMapping("/sessions")
    public String listSessions(Model model, Authentication authentication) {

        List<SessionsEntity> sessions = sessionRepository.findAll();
        model.addAttribute("testSessions", sessions);

        return "sessions/sessions";
    }

    @GetMapping("/sessions/new")
    public String newSession(Authentication authentication, Model model) {

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        UserEntity user = null;

        if (isAuthenticated) {
            String userEmail = authentication.getName();
            user = userRepository.findByEmail(userEmail).orElseThrow(RuntimeException::new);
        }

        List<StrategyEntity> strategies = strategyRepository.findAll();
        assert user != null;
        List<ProjectEntity> projects = projectRepository.findByMemberId(user.getId());

        model.addAttribute("strategies", strategies);
        model.addAttribute("projects", projects);

        // Bind empty form
        model.addAttribute("sessionForm", new SessionsEntity());

        return "sessions/create-session";
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
        return "redirect:sessions/sessions";
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
