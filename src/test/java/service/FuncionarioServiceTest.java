package com.industria.service;

import com.industria.model.Funcionario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FuncionarioService — testes unitários")
class FuncionarioServiceTest {

    private FuncionarioService service;
    private List<Funcionario> funcionarios;

    @BeforeEach
    void setUp() {
        service = new FuncionarioService();
        funcionarios = new ArrayList<>(List.of(
                new Funcionario("Maria",  LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João",   LocalDate.of(1990,  5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio",   LocalDate.of(1961,  5,  2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice",  LocalDate.of(1995,  1,  5), new BigDecimal("2234.68"), "Recepcionista")
        ));
    }

    // ── 3.2 Remover por nome ─────────────────────────────────────────────────

    @Test
    @DisplayName("deve remover funcionário pelo nome")
    void deveRemoverPorNome() {
        boolean resultado = service.removerPorNome(funcionarios, "João");

        assertThat(resultado).isTrue();
        assertThat(funcionarios).extracting(Funcionario::getNome)
                .doesNotContain("João");
    }

    @Test
    @DisplayName("não deve alterar lista ao tentar remover nome inexistente")
    void naoDeveRemoverNomeInexistente() {
        int tamanhoOriginal = funcionarios.size();
        boolean resultado = service.removerPorNome(funcionarios, "NomeInexistente");

        assertThat(resultado).isFalse();
        assertThat(funcionarios).hasSize(tamanhoOriginal);
    }

    // ── 3.4 Aumento salarial ─────────────────────────────────────────────────

    @Test
    @DisplayName("deve aplicar aumento de 10% corretamente")
    void deveAplicarAumentoDeZezPorcento() {
        service.aplicarAumento(funcionarios, 10);

        Funcionario maria = funcionarios.stream()
                .filter(f -> f.getNome().equals("Maria"))
                .findFirst().orElseThrow();

        // 2009.44 * 1.10 = 2210.38
        assertThat(maria.getSalario()).isEqualByComparingTo(new BigDecimal("2210.38"));
    }

    @Test
    @DisplayName("deve aplicar aumento a todos os funcionários")
    void deveAplicarAumentoATodos() {
        List<BigDecimal> salariosOriginais = funcionarios.stream()
                .map(Funcionario::getSalario)
                .toList();

        service.aplicarAumento(funcionarios, 10);

        for (int i = 0; i < funcionarios.size(); i++) {
            assertThat(funcionarios.get(i).getSalario())
                    .isGreaterThan(salariosOriginais.get(i));
        }
    }

    // ── 3.5 Agrupar por função ────────────────────────────────────────────────

    @Test
    @DisplayName("deve agrupar funcionários por função corretamente")
    void deveAgruparPorFuncao() {
        Map<String, List<Funcionario>> mapa = service.agruparPorFuncao(funcionarios);

        assertThat(mapa).containsKeys("Operador", "Coordenador", "Diretor", "Recepcionista");
        assertThat(mapa.get("Operador")).hasSize(2);
        assertThat(mapa.get("Coordenador")).hasSize(1);
    }

    // ── 3.8 Filtrar por mês de aniversário ────────────────────────────────────

    @Test
    @DisplayName("deve filtrar aniversariantes dos meses 10 e 12")
    void deveFiltrarAniversariantesMeses10e12() {
        List<Funcionario> resultado = service.filtrarPorMesAniversario(funcionarios, 10, 12);

        assertThat(resultado)
                .extracting(Funcionario::getNome)
                .containsExactlyInAnyOrder("Maria", "Miguel");
    }

    @Test
    @DisplayName("deve retornar lista vazia quando nenhum aniversariante no mês")
    void deveRetornarVazioParaMesSemAniversario() {
        List<Funcionario> resultado = service.filtrarPorMesAniversario(funcionarios, 2);
        assertThat(resultado).isEmpty();
    }

    // ── 3.9 Funcionário mais velho ────────────────────────────────────────────

    @Test
    @DisplayName("deve identificar o funcionário mais velho (Caio, 1961)")
    void deveIdentificarFuncionarioMaisVelho() {
        Optional<Funcionario> maisVelho = service.funcionarioMaisVelho(funcionarios);

        assertThat(maisVelho).isPresent();
        assertThat(maisVelho.get().getNome()).isEqualTo("Caio");
    }

    // ── 3.10 Ordenação alfabética ─────────────────────────────────────────────

    @Test
    @DisplayName("deve ordenar funcionários alfabeticamente")
    void deveOrdenarAlfabeticamente() {
        List<Funcionario> ordenados = service.ordenarAlfabeticamente(funcionarios);

        assertThat(ordenados)
                .extracting(Funcionario::getNome)
                .isSortedAccordingTo(String::compareTo);
    }

    // ── 3.11 Total de salários ────────────────────────────────────────────────

    @Test
    @DisplayName("deve calcular o total dos salários corretamente")
    void deveCalcularTotalSalarios() {
        BigDecimal total = service.totalSalarios(funcionarios);

        // 2009.44 + 2284.38 + 9836.14 + 19119.88 + 2234.68
        assertThat(total).isEqualByComparingTo(new BigDecimal("35484.52"));
    }

    // ── 3.12 Salários mínimos ─────────────────────────────────────────────────

    @Test
    @DisplayName("deve calcular quantos salários mínimos o funcionário recebe")
    void deveCalcularSalariosMinimos() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        BigDecimal resultado = service.salariosMinimos(new BigDecimal("2424.00"), salarioMinimo);

        assertThat(resultado).isEqualByComparingTo(new BigDecimal("2.00"));
    }
}