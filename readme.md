# IFS Eventos - Gestor de Eventos Acadêmicos

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20Desenvolvimento-blue?style=for-the-badge" alt="Status">
  <img src="https://img.shields.io/badge/Ambiente-Acad%C3%Aamico-orange?style=for-the-badge" alt="Ambiente">
  <img src="https://img.shields.io/badge/Conformidade-LGPD-green?style=for-the-badge" alt="LGPD">
</p>

## 👥 Autores
* **Luiz Eduardo da Silva Albuquerque**
* **Lorena Vieira Ribeiro**

---

## 📌 Sobre o Projeto
O **IFS Eventos** é uma plataforma centralizada para a gestão, divulgação e participação em eventos acadêmicos do Instituto Federal de Sergipe (IFS). O sistema visa otimizar todo o ecossistema de extensão e pesquisa, desde a criação do evento por docentes até o controle de presença automatizado e a emissão de certificados autenticáveis para discentes.

---

## 🎭 Análise de Ecossistema (Atores & Stakeholders)

### Atores (Usuários Diretos)
* **Discentes:** Alunos que buscam eventos, realizam inscrições e gerenciam suas participações.
* **Docentes/Organizadores:** Professores ou técnicos que criam, gerenciam e monitoram os eventos.
* **Administrador do Sistema:** Equipe de TI do IFS responsável pela manutenção e suporte do software.

### Stakeholders (Interessados Indiretos)
* **Direção do IFS:** Monitoramento de relatórios de atividades de extensão e engajamento acadêmico.
* **Departamento de TI (DTI):** Governança da infraestrutura de dados e segurança da informação.
* **Setor de Extensão/Pesquisa:** Validação de certificados emitidos e consolidação de métricas institucionais.

---

## 🗄️ Arquitetura de Banco de Dados (Modelo Relacional)

O banco de dados é estruturado em tabelas normalizadas com forte integridade referencial. Abaixo encontram-se as especificações das entidades do sistema:

### 1. `Usuarios` (Sincronizado institucionalmente)
* `id_usuario` (PK): `UUID` ou `Integer`
* `matricula` (Unique): `String`
* `nome`: `String`
* `email_institucional`: `String`
* `telefone`: `String`
* `tipo_usuario`: `Enum ('Discente', 'Docente', 'Admin')`
* `id_curso` / `id_departamento` (FK): Referencia `Cursos`

### 2. `Locais`
* `id_local` (PK): `UUID` ou `Integer`
* `nome_sala`: `String`
* `bloco`: `String`
* `capacidade_real`: `Integer`
* `id_curso` / `id_departamento` (FK): Referencia `Cursos`

### 3. `Eventos`
* `id_evento` (PK): `UUID` ou `Integer`
* `titulo`: `String`
* `resumo`: `Text`
* `data_inicio`: `Datetime`
* `data_fim`: `Datetime`
* `capacidade_maxima`: `Integer`
* `id_organizador` (FK): Referencia `Usuarios`

### 4. `Subeventos`
* `id_subevento` (PK): `UUID` ou `Integer`
* `titulo`: `String`
* `resumo`: `Text`
* `data_inicio`: `Datetime`
* `data_fim`: `Datetime`
* `local`: `String`
* `valor`: `Decimal (10,2)` (0.00 se gratuito)
* `capacidade_maxima`: `Integer`
* `id_evento` (FK): Referencia `Eventos`

### 5. `InscricoesEvento` (Relação N:M)
* `id_inscricaoE` (PK): `UUID`
* `id_usuario` (FK): Referencia `Usuarios`
* `id_evento` (FK): Referencia `Eventos`
* `data_inscricao`: `Timestamp` (Default: `NOW`)

### 6. `InscricoesSubEvento` (Relação N:M)
* `id_inscricaoSE` (PK): `UUID`
* `id_usuario` (FK): Referencia `Usuarios`
* `id_subevento` (FK): Referencia `Subeventos`
* `data_inscricao`: `Timestamp` (Default: `NOW`)
* `status_presenca`: `Boolean` (Default: `FALSE`)

### 7. `Certificados` (Pós-Evento)
* `id_participacao` (PK): `UUID`
* `id_inscricaoSE` (FK): Referencia `InscricoesSubEvento`
* `carga_horaria`: `Integer`
* `hash_autenticidade` (Unique): `String`
* `data_emissao`: `Timestamp` (Default: `NOW`)

---

## 📊 Modelagem Conceitual & Regras de Mapeamento (DER)

1. **Núcleo Acadêmico:** Múltiplos `Usuarios` e `Locais` pertencem a uma estrutura centralizada de `Cursos/Departamentos`. Um Docente pode gerenciar `N` `Eventos`.
2. **Composição Exclusiva:** Um `Evento` macro possui uma relação de agregação/composição forte de `1:N` para `Subeventos`. Cada `Subevento` está associado a um `Local` físico regulando a lotação.
3. **Muitos-para-Muitos (N:M):** As tabelas `InscricoesEvento` e `InscricoesSubEvento` resolvem as associações de usuários com as agendas desejadas.
4. **Certificação:** Uma `InscricaoSubEvento` confirmada com `status_presenca = TRUE` dispara a emissão de `1` `Certificado` contendo um `hash_autenticidade` para verificação externa.


## 📊 Diagrama de Entidade-Relacionamento (DER)

```mermaid
erDiagram
    CURSOS ||--o{ USUARIOS : "possui"
    CURSOS ||--o{ LOCAIS : "vincula"
    USUARIOS ||--o{ EVENTOS : "organiza"
    EVENTOS ||--o{ SUBEVENTOS : "compoe"
    LOCAIS ||--o{ SUBEVENTOS : "sedia"

    USUARIOS ||--o{ INSCRICOES_EVENTO : "realiza"
    EVENTOS ||--o{ INSCRICOES_EVENTO : "recebe"

    USUARIOS ||--o{ INSCRICOES_SUBEVENTO : "participa"
    SUBEVENTOS ||--o{ INSCRICOES_SUBEVENTO : "aloca"

    INSCRICOES_SUBEVENTO ||--o| CERTIFICADOS : "gera"

    CURSOS {
        int id PK
        string nomeCurso
        string departamento
    }

    USUARIOS {
        int id PK
        string matricula UK
        string nome
        string emailInstitucional
        string telefone
        enum tipoUsuario
        int id_curso FK
    }

    LOCAIS {
        int id PK
        string nomeSala
        string bloco
        int capacidadeReal
        int id_curso FK
    }

    EVENTOS {
        int id PK
        string titulo
        text resumo
        datetime dataInicio
        datetime dataFim
        int capacidadeMaxima
        int id_organizador FK
    }

    SUBEVENTOS {
        int id PK
        string titulo
        text resumo
        datetime dataInicio
        datetime dataFim
        decimal valor
        int capacidadeMaxima
        int id_evento FK
        int id_local FK
    }

    INSCRICOES_EVENTO {
        int id_usuario FK
        int id_evento FK
    }

    INSCRICOES_SUBEVENTO {
        int id_usuario FK
        int id_subevento FK
    }

    CERTIFICADOS {
        int id PK
        int id_usuario FK
        int id_subevento FK
        int cargaHoraria
        string hashAutenticidade UK
        datetime dataEmissao
    }
```

---


## 📐 Diagrama de Classes UML (Mermaid)

O diagrama abaixo descreve a estrutura de classes do domínio e suas respectivas relações.

```mermaid
classDiagram
    class Usuario {
        +UUID id_usuario
        +String matricula
        +String nome
        +String email_institucional
        +Enum tipo_usuario
        +inscreverEvento(Evento e)
        +gerarRelatorio()
    }

    class Local {
        +UUID id_local
        +String nome_sala
        +String bloco
        +Integer capacidade_real
    }

    class Evento {
        +UUID id_evento
        +String titulo
        +String resumo
        +DateTime data_inicio
        +DateTime data_fim
        +Integer capacidade_maxima
        +publicar()
    }

    class Subevento {
        +UUID id_subevento
        +String titulo
        +String resumo
        +DateTime data_inicio
        +DateTime data_fim
        +Decimal valor
        +Integer capacidade_maxima
    }

    class InscricaoSubEvento {
        +UUID id_inscricaoSE
        +DateTime data_inscricao
        +Boolean status_presenca
        +realizarCheckIn()
    }

    class Certificado {
        +UUID id_certificado
        +Integer carga_horaria
        +String hash_autenticidade
        +DateTime data_emissao
        +validarHash()
    }

    class Curso {
        +UUID id_curso
        +String nome_curso
        +String departamento
    }

    %% Relacionamentos
    Usuario "n" --> "1" Curso : pertence a
    Local "n" --> "1" Curso : vinculado a
    Evento "n" --> "1" Usuario : organizado por
    Subevento "n" --> "1" Evento : compõe
    Subevento "n" --> "1" Local : ocorre em

    InscricaoSubEvento "n" --> "1" Usuario : realizada por
    InscricaoSubEvento "n" --> "1" Subevento : refere-se a

    Certificado "1" -- "1" InscricaoSubEvento : gera
```
