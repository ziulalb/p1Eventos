# Diagrama de Entidade-Relacionamento (DER)

**Projeto:** Gestor de Eventos Acadêmicos (IFS Eventos)  
**Autores:** Luiz Eduardo da Silva Albuquerque e Lorena Vieira Ribeiro  

Este arquivo contém a modelagem lógica do banco de dados do sistema **IFS Eventos**, utilizando a especificação nativa Mermaid do GitHub para renderização gráfica automatizada.

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
        int id_curso PK
        string nome_curso
        string departamento
    }

    USUARIOS {
        int id_usuario PK
        string matricula UK
        string nome
        string email_institucional
        string telefone
        enum tipo_usuario
        int id_curso FK
    }

    LOCAIS {
        int id_local PK
        string nome_sala
        string bloco
        int capacidade_real
        int id_curso FK
    }

    EVENTOS {
        int id_evento PK
        string titulo
        text resumo
        datetime data_inicio
        datetime data_fim
        int capacidade_maxima
        int id_organizador FK
    }

    SUBEVENTOS {
        int id_subevento PK
        string titulo
        text resumo
        datetime data_inicio
        datetime data_fim
        decimal valor
        int capacidade_maxima
        int id_evento FK
        int id_local FK
    }

    INSCRICOES_EVENTO {
        uuid id_inscricaoE PK
        int id_usuario FK
        int id_evento FK
        timestamp data_inscricao
        boolean status_presenca
    }

    INSCRICOES_SUBEVENTO {
        uuid id_inscricaoSE PK
        int id_usuario FK
        int id_subevento FK
        timestamp data_inscricao
        boolean status_presenca
    }

    CERTIFICADOS {
        uuid id_participacao PK
        uuid id_inscricaoSE FK
        int carga_horaria
        string hash_autenticidade
        timestamp data_emissao
    }
