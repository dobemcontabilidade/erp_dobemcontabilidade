package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DescontoPlanoContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoPlanoContabil.class);
        DescontoPlanoContabil descontoPlanoContabil1 = getDescontoPlanoContabilSample1();
        DescontoPlanoContabil descontoPlanoContabil2 = new DescontoPlanoContabil();
        assertThat(descontoPlanoContabil1).isNotEqualTo(descontoPlanoContabil2);

        descontoPlanoContabil2.setId(descontoPlanoContabil1.getId());
        assertThat(descontoPlanoContabil1).isEqualTo(descontoPlanoContabil2);

        descontoPlanoContabil2 = getDescontoPlanoContabilSample2();
        assertThat(descontoPlanoContabil1).isNotEqualTo(descontoPlanoContabil2);
    }

    @Test
    void calculoPlanoAssinaturaTest() {
        DescontoPlanoContabil descontoPlanoContabil = getDescontoPlanoContabilRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        descontoPlanoContabil.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(descontoPlanoContabil.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContabil()).isEqualTo(descontoPlanoContabil);

        descontoPlanoContabil.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(descontoPlanoContabil.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContabil()).isNull();

        descontoPlanoContabil.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(descontoPlanoContabil.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContabil()).isEqualTo(descontoPlanoContabil);

        descontoPlanoContabil.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(descontoPlanoContabil.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getDescontoPlanoContabil()).isNull();
    }

    @Test
    void periodoPagamentoTest() {
        DescontoPlanoContabil descontoPlanoContabil = getDescontoPlanoContabilRandomSampleGenerator();
        PeriodoPagamento periodoPagamentoBack = getPeriodoPagamentoRandomSampleGenerator();

        descontoPlanoContabil.setPeriodoPagamento(periodoPagamentoBack);
        assertThat(descontoPlanoContabil.getPeriodoPagamento()).isEqualTo(periodoPagamentoBack);

        descontoPlanoContabil.periodoPagamento(null);
        assertThat(descontoPlanoContabil.getPeriodoPagamento()).isNull();
    }

    @Test
    void planoContabilTest() {
        DescontoPlanoContabil descontoPlanoContabil = getDescontoPlanoContabilRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        descontoPlanoContabil.setPlanoContabil(planoContabilBack);
        assertThat(descontoPlanoContabil.getPlanoContabil()).isEqualTo(planoContabilBack);

        descontoPlanoContabil.planoContabil(null);
        assertThat(descontoPlanoContabil.getPlanoContabil()).isNull();
    }
}
