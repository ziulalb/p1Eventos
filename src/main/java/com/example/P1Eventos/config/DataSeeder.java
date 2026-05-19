package com.example.P1Eventos.config;

import com.example.P1Eventos.entities.*;
import com.example.P1Eventos.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CursoRepository cursoRepo;
    private final UsuarioRepository usuarioRepo;
    private final LocalRepository localRepo;
    private final EventoRepository eventoRepo;
    private final SubeventoRepository subeventoRepo;
    private final CertificadoRepository certificadoRepo;

    @Override
    public void run(String... args) {

        // --- Cursos ---
        Curso ads = cursoRepo.save(Curso.builder()
                .nomeCurso("Análise e Desenvolvimento de Sistemas")
                .departamento("Tecnologia da Informação")
                .build());

        Curso civil = cursoRepo.save(Curso.builder()
                .nomeCurso("Engenharia Civil")
                .departamento("Engenharia")
                .build());

        // --- Usuários ---
        usuarioRepo.save(Usuario.builder()
                .matricula("ADM001")
                .nome("Carlos Administrador")
                .emailInstitucional("carlos.admin@ifs.edu.br")
                .telefone("(79) 99999-0001")
                .tipoUsuario(TipoUsuario.ADMIN)
                .curso(ads)
                .build());

        Usuario docente1 = usuarioRepo.save(Usuario.builder()
                .matricula("DOC001")
                .nome("Professora Ana Lima")
                .emailInstitucional("ana.lima@ifs.edu.br")
                .telefone("(79) 99999-0002")
                .tipoUsuario(TipoUsuario.DOCENTE)
                .curso(ads)
                .build());

        Usuario docente2 = usuarioRepo.save(Usuario.builder()
                .matricula("DOC002")
                .nome("Professor João Santos")
                .emailInstitucional("joao.santos@ifs.edu.br")
                .telefone("(79) 99999-0003")
                .tipoUsuario(TipoUsuario.DOCENTE)
                .curso(civil)
                .build());

        Usuario discente1 = usuarioRepo.save(Usuario.builder()
                .matricula("DIS001")
                .nome("Maria Oliveira")
                .emailInstitucional("maria.oliveira@academico.ifs.edu.br")
                .tipoUsuario(TipoUsuario.DISCENTE)
                .curso(ads)
                .build());

        Usuario discente2 = usuarioRepo.save(Usuario.builder()
                .matricula("DIS002")
                .nome("Pedro Costa")
                .emailInstitucional("pedro.costa@academico.ifs.edu.br")
                .tipoUsuario(TipoUsuario.DISCENTE)
                .curso(ads)
                .build());

        Usuario discente3 = usuarioRepo.save(Usuario.builder()
                .matricula("DIS003")
                .nome("Fernanda Silva")
                .emailInstitucional("fernanda.silva@academico.ifs.edu.br")
                .tipoUsuario(TipoUsuario.DISCENTE)
                .curso(civil)
                .build());

        // --- Locais ---
        Local auditorio = localRepo.save(Local.builder()
                .nomeSala("Auditório Principal")
                .bloco("A")
                .capacidadeReal(200)
                .curso(ads)
                .build());

        Local lab = localRepo.save(Local.builder()
                .nomeSala("Laboratório de Informática 1")
                .bloco("B")
                .capacidadeReal(30)
                .curso(ads)
                .build());

        Local salaConferencias = localRepo.save(Local.builder()
                .nomeSala("Sala de Conferências")
                .bloco("C")
                .capacidadeReal(50)
                .curso(civil)
                .build());

        // --- Eventos ---
        Evento semanaInfo = eventoRepo.save(Evento.builder()
                .titulo("Semana de Tecnologia IFS 2025")
                .resumo("Evento anual de tecnologia com palestras, workshops e hackathon.")
                .dataInicio(LocalDateTime.of(2025, 10, 13, 8, 0))
                .dataFim(LocalDateTime.of(2025, 10, 17, 18, 0))
                .capacidadeMaxima(300)
                .organizador(docente1)
                .build());

        Evento semanaEng = eventoRepo.save(Evento.builder()
                .titulo("Semana de Engenharia Civil IFS 2025")
                .resumo("Evento com foco em construção sustentável, materiais e projetos.")
                .dataInicio(LocalDateTime.of(2025, 11, 3, 8, 0))
                .dataFim(LocalDateTime.of(2025, 11, 7, 18, 0))
                .capacidadeMaxima(150)
                .organizador(docente2)
                .build());

        // --- Subeventos ---
        Subevento palestra1 = subeventoRepo.save(Subevento.builder()
                .titulo("Palestra: IA na Educação")
                .resumo("Tendências de Inteligência Artificial aplicadas ao ensino superior.")
                .dataInicio(LocalDateTime.of(2025, 10, 13, 9, 0))
                .dataFim(LocalDateTime.of(2025, 10, 13, 11, 0))
                .valor(BigDecimal.ZERO)
                .capacidadeMaxima(200)
                .evento(semanaInfo)
                .local(auditorio)
                .build());

        Subevento workshop1 = subeventoRepo.save(Subevento.builder()
                .titulo("Workshop: Spring Boot com JPA")
                .resumo("Desenvolvimento de APIs REST com Spring Boot e Spring Data JPA.")
                .dataInicio(LocalDateTime.of(2025, 10, 14, 14, 0))
                .dataFim(LocalDateTime.of(2025, 10, 14, 17, 0))
                .valor(new BigDecimal("50.00"))
                .capacidadeMaxima(30)
                .evento(semanaInfo)
                .local(lab)
                .build());

        Subevento palestra2 = subeventoRepo.save(Subevento.builder()
                .titulo("Palestra: BIM na Construção Civil")
                .resumo("Aplicação do Building Information Modeling em obras públicas.")
                .dataInicio(LocalDateTime.of(2025, 11, 3, 10, 0))
                .dataFim(LocalDateTime.of(2025, 11, 3, 12, 0))
                .valor(BigDecimal.ZERO)
                .capacidadeMaxima(50)
                .evento(semanaEng)
                .local(salaConferencias)
                .build());

        Subevento minicurso1 = subeventoRepo.save(Subevento.builder()
                .titulo("Minicurso: Materiais Sustentáveis")
                .resumo("Uso de materiais de baixo impacto ambiental na construção civil.")
                .dataInicio(LocalDateTime.of(2025, 11, 4, 9, 0))
                .dataFim(LocalDateTime.of(2025, 11, 4, 12, 0))
                .valor(new BigDecimal("30.00"))
                .capacidadeMaxima(50)
                .evento(semanaEng)
                .local(salaConferencias)
                .build());

        // --- Inscrições em Eventos (N:N via inscricoes_evento) ---
        discente1.getEventosInscritos().add(semanaInfo);
        discente1.getEventosInscritos().add(semanaEng);
        discente1.getSubeventosInscritos().add(palestra1);
        discente1.getSubeventosInscritos().add(workshop1);
        discente1.getSubeventosInscritos().add(palestra2);
        usuarioRepo.save(discente1);

        discente2.getEventosInscritos().add(semanaInfo);
        discente2.getSubeventosInscritos().add(palestra1);
        discente2.getSubeventosInscritos().add(workshop1);
        usuarioRepo.save(discente2);

        discente3.getEventosInscritos().add(semanaEng);
        discente3.getSubeventosInscritos().add(palestra2);
        discente3.getSubeventosInscritos().add(minicurso1);
        usuarioRepo.save(discente3);

        // --- Certificados ---
        certificadoRepo.save(Certificado.builder()
                .usuario(discente1)
                .subevento(palestra1)
                .cargaHoraria(2)
                .hashAutenticidade("a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2")
                .dataEmissao(LocalDateTime.of(2025, 10, 13, 11, 30))
                .build());

        certificadoRepo.save(Certificado.builder()
                .usuario(discente1)
                .subevento(workshop1)
                .cargaHoraria(3)
                .hashAutenticidade("b2c3d4e5f6b2c3d4e5f6b2c3d4e5f6b2c3d4e5f6b2c3d4e5f6b2c3d4e5f6b2c3")
                .dataEmissao(LocalDateTime.of(2025, 10, 14, 17, 30))
                .build());

        certificadoRepo.save(Certificado.builder()
                .usuario(discente2)
                .subevento(palestra1)
                .cargaHoraria(2)
                .hashAutenticidade("c3d4e5f6c3d4e5f6c3d4e5f6c3d4e5f6c3d4e5f6c3d4e5f6c3d4e5f6c3d4e5f6")
                .dataEmissao(LocalDateTime.of(2025, 10, 13, 11, 30))
                .build());

        certificadoRepo.save(Certificado.builder()
                .usuario(discente3)
                .subevento(palestra2)
                .cargaHoraria(2)
                .hashAutenticidade("d4e5f6d4e5f6d4e5f6d4e5f6d4e5f6d4e5f6d4e5f6d4e5f6d4e5f6d4e5f6d4e5")
                .dataEmissao(LocalDateTime.of(2025, 11, 3, 12, 30))
                .build());

        System.out.println("\n=== Seeding concluído com sucesso! ===");
        System.out.println("Cursos:      " + cursoRepo.count());
        System.out.println("Usuários:    " + usuarioRepo.count());
        System.out.println("Locais:      " + localRepo.count());
        System.out.println("Eventos:     " + eventoRepo.count());
        System.out.println("Subeventos:  " + subeventoRepo.count());
        System.out.println("Certificados:" + certificadoRepo.count());
        System.out.println("H2 Console → http://localhost:8080/h2-console");
        System.out.println("  JDBC URL: jdbc:h2:mem:p1eventos\n");
    }
}
