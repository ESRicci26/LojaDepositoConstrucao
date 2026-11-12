# LojaDepositoConstrucao

Aplicação web para gestão de uma loja e depósito de materiais de construção. Permite administrar clientes (com telefones e endereços), produtos e estoque, registrar vendas e acompanhar pagamentos por meio de relatórios.

## Tecnologias/Arquitetura

- Java 21
- Maven
- Spring Boot 3.5 (Web, Validation, JDBC)
- JdbcTemplate (acesso a dados sem ORM)
- PostgreSQL
- Thymeleaf, HTML5 e CSS3 responsivos (sem dependências externas)
- BCrypt (`spring-security-crypto`) para as senhas dos administradores
- Clean Code
- SOLID
- Arquitetura Limpa (Clean Architecture / Hexagonal)

## Funcionalidades

- **Login de administrador** com sessão e proteção de todas as páginas.
- **Clientes:** listar, inserir, atualizar e deletar, com múltiplos telefones e endereços.
- **Produtos:** listar, inserir, atualizar e deletar, com controle de estoque.
- **Vendas:** registro com vários produtos, cálculo automático do total e baixa de estoque.
- **Relatórios:** listagem de vendas, detalhes, registro de pagamento e exclusão de venda.

## Regras de negócio

- Clientes do tipo **Novo** só podem comprar informando a data de pagamento.
- A venda é considerada **paga** quando possui data de pagamento.
- O valor da venda é sempre calculado pelo sistema a partir do preço atual dos produtos.
- Não é permitido vender quantidade superior ao estoque disponível.
- Ao excluir uma venda, os produtos retornam ao estoque.
- Produtos associados a vendas e clientes com vendas não podem ser excluídos.
- CPF, RG, telefone e endereço de cliente não podem se repetir.

## Arquitetura

O projeto segue os princípios da Arquitetura Limpa (Hexagonal), com dependências apontando sempre para o centro:

```
br.com.javaricci
├── dominio                 Entidades e exceções de negócio (sem dependência de framework web)
│   ├── modelo
│   └── excecao
├── aplicacao               Regras de negócio e contratos
│   ├── porta.entrada       Casos de uso (interfaces usadas pelos controladores)
│   ├── porta.saida         Contratos de repositórios e serviços externos
│   ├── servico             Implementação dos casos de uso
│   └── modelo              Comandos e modelos de consulta
├── infraestrutura          Implementações técnicas
│   ├── persistencia        Repositórios com JdbcTemplate
│   └── seguranca           Codificador de senhas BCrypt
└── apresentacao.web        Interface web
    ├── controlador         Controladores Spring MVC
    ├── formulario          Objetos de formulário com validação
    └── seguranca           Interceptador de autenticação
```

Princípios SOLID aplicados:

- **Responsabilidade única:** cada classe tem um papel claro (controlador, serviço, repositório, formulário).
- **Aberto/fechado e inversão de dependência:** serviços dependem de interfaces (portas); a persistência pode ser trocada sem alterar as regras de negócio.
- **Segregação de interfaces:** um caso de uso e um repositório por assunto.
- **Substituição de Liskov:** qualquer implementação de uma porta pode ser usada no lugar de outra.

Cada repositório expõe métodos separados de **inserir**, **atualizar**, **deletar** e **listar**.

## Banco de dados

Scripts em `banco/`:

| Arquivo | Finalidade |
|---|---|
| `01_criar_banco.sql` | Cria o banco `lojadepositoconstrucao` |
| `02_criar_tabelas.sql` | Cria as tabelas e os dados essenciais (loja e administrador padrão) |
| `03_dados_exemplo.sql` | Dados de exemplo opcionais (clientes, produtos e vendas) |
|Se utilizar o DUMP na raiz do projeto "lojadepositoconstrucao.sql" não precisa executar os script acima, exceto a criação do database. |


```bash
psql -U postgres -f banco/01_criar_banco.sql
psql -U postgres -d lojadepositoconstrucao -f banco/02_criar_tabelas.sql
psql -U postgres -d lojadepositoconstrucao -f banco/03_dados_exemplo.sql   # opcional
```

### Tabelas

`tb_administrador`, `tb_cliente`, `tb_endereco_cliente`, `tb_telefone_cliente`, `tb_loja`, `tb_endereco_loja`, `tb_telefone_loja`, `tb_produto`, `tb_venda`, `tb_produto_venda`.

## Como executar

Pré-requisitos: JDK 21, Maven 3.9+ e PostgreSQL.

1. Crie o banco conforme a seção anterior.
2. Configure a conexão em `src/main/resources/application.properties` ou por variáveis de ambiente:

   | Variável | Padrão |
   |---|---|
   | `BD_URL` | `jdbc:postgresql://localhost:5432/lojadepositoconstrucao` |
   | `BD_USUARIO` | `postgres` |
   | `BD_SENHA` | `postgres` |

3. Execute:

   ```bash
   mvn spring-boot:run
   ```

4. Acesse <http://localhost:8080>.

### Acesso inicial

- Usuário: `admin123`
- Senha: `admin123`

Altere essas credenciais em ambiente real. A senha é armazenada com hash BCrypt na coluna `no_senha` da tabela `tb_administrador`.

### Testes

```bash
mvn test
```

### Gerar o pacote executável

```bash
mvn clean package
java -jar target/loja-deposito-construcao-1.0.0.jar
```
