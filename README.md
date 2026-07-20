<h1 align="center"> 🐾 AdotaPet </h1>

<p align="center">
  <b>Sistema Full-Stack de Gerenciamento de Adoção de Animais</b> <br>
  <i>Projeto prático desenvolvido para as disciplinas de Banco de Dados (CSI440 e CSI602) - UFOP</i>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5" />
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3" />
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" alt="JavaScript" />
</p>

<hr>

## 📖 Sobre o Projeto
O **AdotaPet** foi construído com o objetivo de facilitar e organizar o processo de adoção em abrigos de animais. O sistema gerencia todo o ciclo de vida da adoção: desde o cadastro de pets disponíveis e o registro de interessados, até a efetivação da adoção.

**Destaque Acadêmico:** Cumprindo os requisitos da disciplina, o acesso ao banco de dados relacional foi desenvolvido **sem o uso de ORMs** (como Hibernate/JPA). Toda a comunicação é feita de forma nativa utilizando escrita direta de `SQL` através do `Spring JDBC`.

---

## ✨ Funcionalidades

- 🐶 **Gestão de Pets:** Cadastro de animais com informações de raça, porte e disponibilidade.
- 👤 **Gestão de Adotantes:** Registro seguro de adotantes garantindo unicidade por CPF.
- 🤝 **Adoção Transacional:** Processo de adoção que vincula o adotante ao pet e altera o status do animal simultaneamente, garantindo a integridade dos dados (`@Transactional`).
- 💉 **Controle de Vacinas:** Estrutura relacional para registrar o histórico de vacinação dos animais.

---

## 🛠️ Tecnologias Utilizadas

### Back-end
- **Java 21**
- **Spring Boot 3**
- **MySQL** 
- **Maven**

### Front-end
- **HTML5**
- **CSS3**
- **JavaScript**

---

## 🗄️ Modelagem do Banco de Dados

O banco de dados segue a seguinte estrutura relacional central:

- `Animal` (id_animal, raca, nome, porte, status_adocao)
- `Adotante` (cpf, nome, celular)
- `Adocao` (id_adocao, id_animal, cpf_adotante, data_adocao)
- `Vacina` (id_vacina, nome, fabricante)
- `Vacinacao` (id_animal, id_vacina, data_aplicacao)

---

## 🚀 Como executar o projeto localmente

### 1. Preparando o Banco de Dados
Crie um banco de dados no seu MySQL chamado `adotapet`:
```sql
CREATE DATABASE adotapet;
```

### 2. Rodando o Back-end (API)
1. Clone este repositório:
   ```bash
   git clone https://github.com/Viniciussilva107/AdotaPet.git
   ```
2. Acesse o arquivo `src/main/resources/application.properties` e atualize com sua senha do MySQL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/adotapet
   spring.datasource.username=root
   spring.datasource.password=sua_senha
   ```
3. Execute a classe principal `AdotaPetApplication.java` na sua IDE de preferência (IntelliJ, Eclipse, etc.). A API estará disponível em `http://localhost:8080`.

### 3. Rodando o Front-end
1. Navegue até a pasta do Front-end no repositório clonado.
2. Basta abrir o arquivo principal (`index.html`) diretamente no seu navegador.
3. Recomendo utilizar o VS Code com a extensão **Live Server**.

---

<p align="center">
  Desenvolvido por <b>Vinícius Silva de Oliveira</b> para a Universidade Federal de Ouro Preto (UFOP).
</p>
