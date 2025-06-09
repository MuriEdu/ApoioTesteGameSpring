# 🎮 Sistema de Apoio a Testes Exploratórios em Video Games

Este é um sistema web para suporte à execução de **testes exploratórios (TE)** no contexto de **jogos digitais**. O objetivo principal é auxiliar testadores (game testers) na criação, condução e gerenciamento de sessões de testes, baseando-se em estratégias adaptadas e validadas em pesquisas científicas.

## 🧪 Contexto

Testes exploratórios são uma forma manual de testes de software guiada pela experiência e julgamento do testador, eficaz na descoberta de bugs sob a perspectiva do usuário.

Neste projeto, estratégias tradicionais de testes exploratórios foram adaptadas para o domínio de **video games**, com base nos seguintes estudos:

- 📄 [Artigo Científico - Link 1 (Drive)](https://drive.google.com/file/d/1laobzIyr1j2K4ZYzarShJ0VFfmPSP3Yz/view?usp=sharing)
- 📄 [Artigo Científico - Link 2 (Drive)](https://drive.google.com/file/d/1OFqIHKzZA8LekNDceDZ9eKnMYCv-SDBO/view?usp=sharing)

O sistema permite que testadores planejem suas sessões com base nessas estratégias, registrem bugs durante a execução e acompanhem todo o ciclo de vida da sessão.

---

## 💻 Tecnologias Utilizadas

- **Backend:**
    - Spring MVC
    - Spring Data JPA
    - Spring Security
    - Thymeleaf

- **Frontend:**
    - HTML, CSS, JavaScript

- **Internacionalização:** suporte a **Português** e **Inglês**

- **Build & Deploy:**
    - Apache Maven

---

## 🧱 Arquitetura

O projeto segue o padrão **MVC (Modelo-Visão-Controlador)** para organização da lógica de negócio, interface e dados.

---

## 👤 Perfis de Usuário

- **Visitante:** pode navegar nas estratégias sem realizar login.
- **Testador:** pode criar e gerenciar sessões de teste dentro dos projetos aos quais tem acesso.
- **Administrador:** possui acesso total ao sistema, incluindo gerenciamento de usuários, estratégias, projetos e sessões.

---

## 🔐 Funcionalidades

### ✅ Requisitos Implementados

| Código | Requisito                                                                 |
|--------|---------------------------------------------------------------------------|
| R1     | CRUD de testadores (login de administrador)                              |
| R2     | CRUD de administradores (login de administrador)                         |
| R3     | Cadastro de projetos (login de administrador)                            |
| R4     | Listagem ordenada de projetos                                             |
| R5     | Cadastro e listagem de estratégias (R5 é prioritário)                    |
| R6     | Listagem pública de estratégias                                          |
| R7     | Cadastro de sessões de teste (R7 é prioritário)                          |
| R8     | Gerenciamento do ciclo de vida das sessões (R8 é prioritário)            |
| R9     | Listagem de sessões por projeto                                          |
| R10    | Internacionalização em PT e EN                                            |

---

## 🕹️ Entidades Principais

### Projeto
- Nome
- Descrição
- Data de criação (gerada pelo sistema)
- Membros permitidos

### Sessão de Teste
- ID (gerado pelo sistema)
- Testador responsável (usuário logado)
- Estratégia utilizada
- Tempo da sessão (em minutos)
- Descrição
- Status: `Criado`, `Em execução`, `Finalizado`
- Registro de bugs durante a execução
- Mudança de status com timestamp

### Estratégia
- Nome
- Descrição
- Exemplos
- Dicas
- Imagens associadas