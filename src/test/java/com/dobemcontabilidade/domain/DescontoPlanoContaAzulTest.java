package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContaAzulTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContaAzulTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DescontoPlanoContaAzulTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoPlanoContaAzul.class);
        DescontoPlanoContaAzul descontoPlanoContaAzul1 = getDescontoPlanoContaAzulSample1();
        DescontoPlanoContaAzul descontoPlanoContaAzul2 = new DescontoPlanoContaAzul();
        assertThat(descontoPlanoContaAzul1).isNotEqualTo(descontoPlanoContaAzul2);

        descontoPlanoContaAzul2.setId(descontoPlanoContaAzul1.getId());
        assertThat(descontoPlanoContaAzul1).isEqualTo(descontoPlanoContaAzul2);

        descontoPlanoContaAzul2 = getDescontoPlanoContaAzulSample2();
        assertThat(descontoPlanoContaAzul1).isNotEqualTo(descontoPlanoContaAzul2);
    }

    @Test
    void calculoPlanoAssinaturaTest() {
        DescontoPlanoContaAzul descontoPlanoContaAzul = getDescontoPlanoContaAzulRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        descontoPlanoContaAzul.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(descontoPlanoContaAzul.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContaAzul()).isEqualTo(descontoPlanoContaAzul);

        descontoPlanoContaAzul.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(descontoPlanoContaAzul.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContaAzul()).isNull();

        descontoPlanoContaAzul.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(descontoPlanoContaAzul.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContaAzul()).isEqualTo(descontoPlanoContaAzul);

        descontoPlanoContaAzul.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(descontoPlanoContaAzul.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContaAzul()).isNull();
    }

    @Test
    void planoContaAzulTest() {
        DescontoPlanoContaAzul descontoPlanoContaAzul = getDescontoPlanoContaAzulRandomSampleGenerator();
        PlanoContaAzul planoContaAzulBack = getPlanoContaAzulRandomSampleGenerator();

        descontoPlanoContaAzul.setPlanoContaAzul(planoContaAzulBack);
        assertThat(descontoPlanoContaAzul.getPlanoContaAzul()).isEqualTo(planoContaAzulBack);

        descontoPlanoContaAzul.planoContaAzul(null);
        assertThat(descontoPlanoContaAzul.getPlanoContaAzul()).isNull();
    }

    @Test
    void periodoPagamentoTest() {
        DescontoPlanoContaAzul descontoPlanoContaAzul = getDescontoPlanoContaAzulRandomSampleGenerator();
        PeriodoPagamento periodoPagamentoBack = getPeriodoPagamentoRandomSampleGenerator();

        descontoPlanoContaAzul.setPeriodoPagamento(periodoPagamentoBack);
        assertThat(descontoPlanoContaAzul.getPeriodoPagamento()).isEqualTo(periodoPagamentoBack);

        descontoPlanoContaAzul.periodoPagamento(null);
        assertThat(descontoPlanoContaAzul.getPeriodoPagamento()).isNull();
    }
}
