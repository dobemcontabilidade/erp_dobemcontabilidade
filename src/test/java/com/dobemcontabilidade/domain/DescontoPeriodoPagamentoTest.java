package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DescontoPeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoAssinaturaContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DescontoPeriodoPagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoPeriodoPagamento.class);
        DescontoPeriodoPagamento descontoPeriodoPagamento1 = getDescontoPeriodoPagamentoSample1();
        DescontoPeriodoPagamento descontoPeriodoPagamento2 = new DescontoPeriodoPagamento();
        assertThat(descontoPeriodoPagamento1).isNotEqualTo(descontoPeriodoPagamento2);

        descontoPeriodoPagamento2.setId(descontoPeriodoPagamento1.getId());
        assertThat(descontoPeriodoPagamento1).isEqualTo(descontoPeriodoPagamento2);

        descontoPeriodoPagamento2 = getDescontoPeriodoPagamentoSample2();
        assertThat(descontoPeriodoPagamento1).isNotEqualTo(descontoPeriodoPagamento2);
    }

    @Test
    void periodoPagamentoTest() {
        DescontoPeriodoPagamento descontoPeriodoPagamento = getDescontoPeriodoPagamentoRandomSampleGenerator();
        PeriodoPagamento periodoPagamentoBack = getPeriodoPagamentoRandomSampleGenerator();

        descontoPeriodoPagamento.setPeriodoPagamento(periodoPagamentoBack);
        assertThat(descontoPeriodoPagamento.getPeriodoPagamento()).isEqualTo(periodoPagamentoBack);

        descontoPeriodoPagamento.periodoPagamento(null);
        assertThat(descontoPeriodoPagamento.getPeriodoPagamento()).isNull();
    }

    @Test
    void planoAssinaturaContabilTest() {
        DescontoPeriodoPagamento descontoPeriodoPagamento = getDescontoPeriodoPagamentoRandomSampleGenerator();
        PlanoAssinaturaContabil planoAssinaturaContabilBack = getPlanoAssinaturaContabilRandomSampleGenerator();

        descontoPeriodoPagamento.setPlanoAssinaturaContabil(planoAssinaturaContabilBack);
        assertThat(descontoPeriodoPagamento.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabilBack);

        descontoPeriodoPagamento.planoAssinaturaContabil(null);
        assertThat(descontoPeriodoPagamento.getPlanoAssinaturaContabil()).isNull();
    }
}
