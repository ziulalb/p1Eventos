## 📑 Requisitos do Sistema

### Requisitos de Negócio (RN)
| Código | Descrição |
| :--- | :--- |
| **RN01** | Centralizar a divulgação de eventos acadêmicos do IFS em uma única plataforma. |
| **RN02** | Facilitar o controle de presença para fins de emissão de certificados e horas complementares. |
| **RN03** | Garantir que o acesso seja exclusivo à comunidade acadêmica validada. |
| **RN04** | Otimizar a gestão de ocupação de espaços físicos da instituição com base no limite de capacidade. |

### Requisitos Funcionais (RF)
- [ ] **RF01 (Autenticação Institucional):** Permissão de login exclusivamente via matrícula e senha integrados ao banco de dados do IFS.
- [ ] **RF02 (Cadastro de Eventos):** Permite ao organizador inserir título, data, hora, capacidade máxima e resumo do evento macro.
- [ ] **RF03 (Cadastro de Subeventos):** Permite ao organizador gerenciar subatividades contendo título, data, hora, local, valor (se houver), capacidade e resumo.
- [ ] **RF04 (Exibição Cronológica):** Tela principal com listagem automática de eventos ordenados de forma crescente pela data mais próxima.
- [ ] **RF05 (Gestão de Inscrições):** Inscrição em fluxo simplificado (um clique) em eventos e subeventos, respeitando limites de vagas e conflitos de horários.
- [ ] **RF06 (Controle de Presença):** Registro de participação via ferramenta automatizada (Ex: Check-in digital ou QR Code).
- [ ] **RF07 (Filtros Avançados de Busca):** Busca parametrizada por categoria macro (palestra, workshop, congresso) ou subcategorias (minicursos, oficinas), além de carga horária e docente encarregado.

### Requisitos Não Funcionais (RNF)
* **RNF01 (Segurança e Privacidade):** Arquitetura em total conformidade com a **LGPD**, garantindo a cifragem e proteção contra a exposição de dados sensíveis de usuários a terceiros.
* **RNF02 (Desempenho):** Tempo de resposta para autenticação integrada à base externa corporativa inferior a **2 segundos**.
* **RNF03 (Escalabilidade):** Capacidade para suportar picos de acessos simultâneos durante grandes eventos institucionais (Ex: SEMAC).
* **RNF04 (Usabilidade):** Interface responsiva construída sob padrões internacionais de acessibilidade (Web/Mobile).
* **RNF05 (Disponibilidade):** SLA de disponibilidade estabelecido em **99%**, mitigando downtimes durante os períodos letivos.
