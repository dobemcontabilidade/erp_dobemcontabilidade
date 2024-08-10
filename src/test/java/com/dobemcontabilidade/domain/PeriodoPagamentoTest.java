package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContabilTestSamples.*;
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
    void calculoPlanoAssinaturaTest() {
        PeriodoPagamento periodoPagamento = getPeriodoPagamentoRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        periodoPagamento.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(periodoPagamento.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(periodoPagamento.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPeriodoPagamento()).isNull();

        periodoPagamento.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(periodoPagamento.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(periodoPagamento.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPeriodoPagamento()).isNull();
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
    void descontoPlanoContabilTest() {
        PeriodoPagamento periodoPagamento = getPeriodoPagamentoRandomSampleGenerator();
        DescontoPlanoContabil descontoPlanoContabilBack = getDescontoPlanoContabilRandomSampleGenerator();

        periodoPagamento.addDescontoPlanoContabil(descontoPlanoContabilBack);
        assertThat(periodoPagamento.getDescontoPlanoContabils()).containsOnly(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.removeDescontoPlanoContabil(descontoPlanoContabilBack);
        assertThat(periodoPagamento.getDescontoPlanoContabils()).doesNotContain(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPeriodoPagamento()).isNull();

        periodoPagamento.descontoPlanoContabils(new HashSet<>(Set.of(descontoPlanoContabilBack)));
        assertThat(periodoPagamento.getDescontoPlanoContabils()).containsOnly(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPeriodoPagamento()).isEqualTo(periodoPagamento);

        periodoPagamento.setDescontoPlanoContabils(new HashSet<>());
        assertThat(periodoPagamento.getDescontoPlanoContabils()).doesNotContain(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPeriodoPagamento()).isNull();
    }
}
