#  Iniflex — Gestão de Funcionários

Desenvolvido com Java 17, Maven, JUnit 5 e Docker

---

##  Sobre o Projeto

Aplicação Java que gerencia uma lista de funcionários de uma indústria, implementando operações como inserção, remoção, agrupamento, filtragem e cálculos salariais.

### Tecnologias e decisões técnicas

| Tecnologia | Versão | Por quê? |
|---|---|---|
| Java | 17 (LTS) | Records, sealed classes, text blocks, switch expressions |
| Maven | 3.9+ | Gerenciamento de dependências e build padronizado |
| JUnit 5 | 5.10 | Testes unitários com `@DisplayName` e `@BeforeEach` |
| AssertJ | 3.24 | Assertions fluentes e legíveis |
| Docker | multi-stage | Build reproduzível; imagem final leve com JRE Alpine |

---

##  Estrutura do Projeto

```
industria-funcionarios/
├── src/
│   ├── main/java/com/industria/
│   │   ├── model/
│   │   │   ├── Pessoa.java          # Atributos: nome, dataNascimento
│   │   │   └── Funcionario.java     # Extends Pessoa + salário, função
│   │   ├── service/
│   │   │   └── FuncionarioService.java  # Toda a lógica de negócio
│   │   ├── util/
│   │   │   └── FormatUtil.java      # Formatação de datas e valores
│   │   └── Principal.java           # Classe main com todas as ações
│   └── test/java/com/industria/
│       └── service/
│           ├── FuncionarioServiceTest.java
│           └── FormatUtilTest.java
├── Dockerfile                       # Multi-stage build
├── docker-compose.yml
└── pom.xml
```

---

##  Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.9+ (ou use o wrapper `./mvnw`)
- Docker (opcional)

### 1. Clonar e rodar com Maven

```bash
git clone https://github.com/seu-usuario/industria-funcionarios.git
cd industria-funcionarios

# Compilar e executar
mvn clean package -q
java -jar target/industria-funcionarios.jar
```

### 2. Rodar com Docker

```bash
# Build e execução direta
docker build -t industria-funcionarios .
docker run --rm industria-funcionarios

# Ou com docker-compose
docker-compose up --build
```

### 3. Rodar os testes

```bash
mvn test

# Com relatório detalhado
mvn test -Dsurefire.useFile=false
```

---

##  Testes

A suíte de testes cobre todos os requisitos:

| Classe de Teste | Cenários |
|---|---|
| `FuncionarioServiceTest` | Remoção, aumento, agrupamento, filtro por mês, mais velho, ordenação, total de salários, cálculo de SMs |
| `FormatUtilTest` | Formatação de datas, valores, nulos e proteção de instanciação |

```bash
mvn test
# [INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
```

---

##  Requisitos Implementados

| Item  | Funcionalidade |
|-------|----------------|
| 3.1   | Inserção de todos os funcionários conforme a tabela fornecida |
| 3.2   | Remoção do funcionário **João** da lista |
| 3.3   | Impressão dos dados com formatação:<br>• Data no padrão **dd/MM/yyyy**<br>• Salário no padrão brasileiro (**1.234,56**) |
| 3.4   | Aplicação de aumento salarial de **10%** para todos os funcionários |
| 3.5   | Agrupamento dos funcionários por função utilizando `Map<String, List<Funcionario>>` |
| 3.6   | Exibição dos funcionários organizados por função |
| 3.8   | Filtragem e exibição de funcionários com aniversário nos meses **outubro (10)** e **dezembro (12)** |
| 3.9   | Identificação e exibição do funcionário com maior idade (nome e idade) |
| 3.10  | Ordenação e exibição da lista de funcionários em ordem alfabética |
| 3.11  | Cálculo e exibição do total dos salários |
| 3.12  | Cálculo da quantidade de salários mínimos por funcionário (base: R$ 1212,00) |

---
