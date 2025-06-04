package br.ufscar.dc.dsw.gametest.entities;

import br.ufscar.dc.dsw.gametest.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity(name = "user_tb")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
            message = "O e-mail deve estar no formato correto (ex: usuario@dominio.com)"
    )
    @Column(unique = true, nullable = false)
    private String email;


    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    // Construtor padrão
    public UserEntity() {}

    // Construtor com argumentos
    public UserEntity(String name, String email, String password, Roles role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
