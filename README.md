# 📦 Sistema de Gestão de Usuários e Estoque

Este projeto foi desenvolvido em **Java 8** utilizando **Swing** para a
interface gráfica e **JDBC** para integração com o banco de dados
(Oracle).
Ele permite o **cadastro, login e gerenciamento de usuários**, além de
realizar o **CRUD de itens no estoque**.

------------------------------------------------------------------------

## 🚀 Funcionalidades

### 👤 Usuários

-   **Cadastro de Usuário:** cria novos usuários com nome e e-mail.
-   **Login:** valida os dados de login (nome + e-mail) e acessa o
    sistema.
-   **Atualização de Usuário:** altera nome e e-mail de um usuário
    existente.
-   **Exclusão de Usuário:** remove um usuário do sistema.

### 📦 Estoque

-   **Visualização de Itens:** exibe todos os produtos cadastrados em
    uma tabela.
-   **Cadastro de Itens:** adiciona novos produtos ao estoque (nome,
    quantidade e data de entrada).
-   **Atualização de Itens:** permite editar informações de um produto.
-   **Exclusão de Itens:** remove um produto da tabela de estoque.

------------------------------------------------------------------------

## 🛠 Estrutura do Projeto

    src/
     └── org.example/
         ├── Main.java                 # Classe principal para iniciar o sistema
         ├── config/
         │    └── DatabaseConnectionFactory.java  # Conexão com o banco de dados
         ├── dao/
         │    ├── DatabaseSetupDao.java # Criação das tabelas e estrutura inicial
         │    ├── UsuarioDao.java       # Operações CRUD de usuários
         │    └── ItemDao.java          # Operações CRUD de itens
         ├── model/
         │    ├── Usuario.java          # Classe modelo para usuários
         │    └── Item.java             # Classe modelo para itens
         ├── service/
         │    ├── UsuarioService.java   # Regras de negócio dos usuários
         │    └── ItemService.java      # Regras de negócio dos itens
         ├── ui/
         │    ├── LoginUI.java          # Tela de login
         │    ├── CadastroUI.java       # Tela de cadastro de usuários
         │    └── EstoqueUI.java        # Tela de gerenciamento do estoque
         └── test/
              └── AppTest.java          # Testes JUnit para Usuários e Itens

------------------------------------------------------------------------

## 🗄 Banco de Dados

O sistema utiliza um banco relacional (Oracle).


------------------------------------------------------------------------

## 🧪 Testes

O projeto conta com testes unitários utilizando **JUnit** e
**Mockito**:
- Testa inserção, atualização, exclusão e listagem de usuários.
- Testa inserção, atualização, exclusão e listagem de itens do estoque.

------------------------------------------------------------------------

## 📷 Interfaces

### 🔑 Tela de Login

-   Entrada de **nome** e **e-mail**.
-   Acesso ao sistema caso os dados sejam válidos.

### 📝 Tela de Cadastro de Usuário

-   Campos para nome e e-mail.
-   Botão para registrar novo usuário.

### 📊 Tela de Estoque

-   Exibe tabela com os itens cadastrados.
-   Botões: **Adicionar Produto**, **Atualizar Produto**, **Remover
    Produto**.

------------------------------------------------------------------------

## ▶️ Como Executar

1.  Clone o repositório:

    ``` 
    git clone https://github.com/seu-usuario/seu-repo.git
    ```

2.  Configure o banco de dados no arquivo
    `DatabaseConnectionFactory.java`.

3.  Rode a classe `Main.java`.

4.  Utilize as telas de login, cadastro e estoque normalmente.

------------------------------------------------------------------------

## ✅ Tecnologias Utilizadas

-   **Java 8**
-   **Swing** (para UI)
-   **JDBC** (integração com o banco de dados)
-   **Oracle** (persistência)
-   **JUnit 5** + **Mockito** (testes)

------------------------------------------------------------------------

## 📌 Próximos Passos

-   Adicionar sistema de autenticação com senha.
-   Melhorar validações (ex: impedir nomes/emails duplicados).
-   Criar relatórios de movimentação de estoque.
