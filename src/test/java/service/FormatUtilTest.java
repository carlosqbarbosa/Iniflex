package com.industria.service;

import com.industria.util.FormatUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("FormatUtil — testes unitários")
class FormatUtilTest {

    @Test
    @DisplayName("deve formatar data no padrão dd/MM/yyyy")
    void deveFormatarData() {
        LocalDate data = LocalDate.of(2000, 10, 18);
        assertThat(FormatUtil.formatarData(data)).isEqualTo("18/10/2000");
    }

    @Test
    @DisplayName("deve retornar '-' para data nula")
    void deveRetornarTracoPaDataNula() {
        assertThat(FormatUtil.formatarData(null)).isEqualTo("-");
    }

    @Test
    @DisplayName("deve formatar salário com milhar como ponto e decimal como vírgula")
    void deveFormatarSalarioComSeparadores() {
        BigDecimal valor = new BigDecimal("9836.14");
        assertThat(FormatUtil.formatarSalario(valor)).isEqualTo("9.836,14");
    }

    @Test
    @DisplayName("deve formatar valor abaixo de mil sem separador de milhar")
    void deveFormatarValorAbaixoDeMil() {
        BigDecimal valor = new BigDecimal("999.99");
        assertThat(FormatUtil.formatarSalario(valor)).isEqualTo("999,99");
    }

    @Test
    @DisplayName("deve retornar '-' para salário nulo")
    void deveRetornarTracoPaSalarioNulo() {
        assertThat(FormatUtil.formatarSalario(null)).isEqualTo("-");
    }

    @Test
    @DisplayName("FormatUtil não deve ser instanciável")
    void naoDeveSerInstanciavel() {
        assertThatThrownBy(() -> {
            var constructor = FormatUtil.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }).hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}