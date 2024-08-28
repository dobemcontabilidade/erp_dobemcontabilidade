package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PeriodoPagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoPagamento.class);
        PeriodoPagamento periodoPagamento1 = getPeriodoPagamentoSample1();
        PeriodoPagamento periodoPagamento2 = new PeriodoPagamento();
        assertThat(periodoPagamento1).isNotEqualTo(periodoPagamento2);

        periodoPagamento2.setId(periodoPagamento1.getId());
        assertThat(periodoPagamento1).isEqualTo(periodoPagamento2);

        periodoPagamento2 = getPeriodoPagamentoSample2();
        assertThat(periodoPagamento1).isNotEqualTo(periodoPagamento2);
    }

    @Test
    void assinaturaEmpresaTest() {
        PeriodoPagamento periodoPagamento = getPeriodoPagamentoRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        periodoPagamento.addAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(periodoPagamento.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.removeAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(periodoPagamento.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPeriodoPagamento()).isNull();

        periodoPagamento.assinaturaEmpresas(new HashSet<>(Set.of(assinaturaEmpresaBack)));
        assertThat(periodoPagamento.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.setAssinaturaEmpresas(new HashSet<>());
        assertThat(periodoPagamento.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPeriodoPagamento()).isNull();
    }

    @Test
    void descontoPeriodoPagamentoTest() {
        PeriodoPagamento periodoPagamento = getPeriodoPagamentoRandomSampleGenerator();
        DescontoPeriodoPagamento descontoPeriodoPagamentoBack = getDescontoPeriodoPagamentoRandomSampleGenerator();

        periodoPagamento.addDescontoPeriodoPagamento(descontoPeriodoPagamentoBack);
        assertThat(periodoPagamento.getDescontoPeriodoPagamentos()).containsOnly(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.removeDescontoPeriodoPagamento(descontoPeriodoPagamentoBack);
        assertThat(periodoPagamento.getDescontoPeriodoPagamentos()).doesNotContain(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPeriodoPagamento()).isNull();

        periodoPagamento.descontoPeriodoPagamentos(new HashSet<>(Set.of(descontoPeriodoPagamentoBack)));
        assertThat(periodoPagamento.getDescontoPeriodoPagamentos()).containsOnly(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.setDescontoPeriodoPagamentos(new HashSet<>());
        assertThat(periodoPagamento.getDescontoPeriodoPagamentos()).doesNotContain(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPeriodoPagamento()).isNull();
    }
}
