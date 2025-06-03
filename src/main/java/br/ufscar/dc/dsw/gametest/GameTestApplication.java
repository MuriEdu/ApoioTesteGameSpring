package br.ufscar.dc.dsw.gametest;

import br.ufscar.dc.dsw.gametest.entities.UserEntity;
import br.ufscar.dc.dsw.gametest.enums.Roles;
import br.ufscar.dc.dsw.gametest.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GameTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameTestApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepository) {
        return args -> {
            String adminEmail = "admin@example.com";

            // Check if admin user already exists
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setId(1L);  // You might want to generate IDs differently in real use
                admin.setName("admin");
                admin.setEmail(adminEmail);

                // Encrypt password with BCrypt
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedPassword = encoder.encode("admin123");  // default password
                admin.setPassword(encodedPassword);

                admin.setRole(Roles.ADMIN);

                userRepository.save(admin);
                System.out.println("Default ADMIN user created.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
