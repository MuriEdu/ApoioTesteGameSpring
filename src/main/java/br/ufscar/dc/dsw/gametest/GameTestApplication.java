package br.ufscar.dc.dsw.gametest;

import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import br.ufscar.dc.dsw.gametest.enums.Roles;
import br.ufscar.dc.dsw.gametest.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GameTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameTestApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultUsers(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Create default admin user
            String adminEmail = "admin@example.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setName("admin");
                admin.setEmail(adminEmail);
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Roles.ADMIN);
                userRepository.save(admin);
                System.out.println("Default ADMIN user created.");
            } else {
                System.out.println("Admin user already exists.");
            }

            // List of default testers
            List<UserEntity> testers = List.of(
                    new UserEntity("Et Bilu", "et.bilu@example.com", encoder.encode("test123"), Roles.TESTER),
                    new UserEntity("Arnold Schwarzenegger", "arnold@example.com", encoder.encode("test123"), Roles.TESTER),
                    new UserEntity("Mickey Mouse", "mickey@example.com", encoder.encode("test123"), Roles.TESTER)
            );

            for (UserEntity tester : testers) {
                if (userRepository.findByEmail(tester.getEmail()).isEmpty()) {
                    userRepository.save(tester);
                    System.out.println("Tester user created: " + tester.getName());
                } else {
                    System.out.println("Tester user already exists: " + tester.getName());
                }
            }
        };
    }
}
