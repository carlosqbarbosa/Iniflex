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

| Item | Descrição |
|---|---|---|
| 3.1 | Inserir todos os funcionários |
| 3.2 | Remover funcionário "João" | 
| 3.3 | Imprimir com formato de data e moeda | 
| 3.4 | Aumento de 10% no salário | 
| 3.5 | Agrupar por função em MAP | 
| 3.6 | Imprimir agrupados por função | 
| 3.8 | Aniversariantes nos meses 10 e 12 | 
| 3.9 | Funcionário com maior idade | 
| 3.10 | Lista em ordem alfabética | 
| 3.11 | Total dos salários | 
| 3.12 | Quantidade de salários mínimos por funcionário |

---
