package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContaAzulTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContaAzulTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalculoPlanoAssinaturaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalculoPlanoAssinatura.class);
        CalculoPlanoAssinatura calculoPlanoAssinatura1 = getCalculoPlanoAssinaturaSample1();
        CalculoPlanoAssinatura calculoPlanoAssinatura2 = new CalculoPlanoAssinatura();
        assertThat(calculoPlanoAssinatura1).isNotEqualTo(calculoPlanoAssinatura2);

        calculoPlanoAssinatura2.setId(calculoPlanoAssinatura1.getId());
        assertThat(calculoPlanoAssinatura1).isEqualTo(calculoPlanoAssinatura2);

        calculoPlanoAssinatura2 = getCalculoPlanoAssinaturaSample2();
        assertThat(calculoPlanoAssinatura1).isNotEqualTo(calculoPlanoAssinatura2);
    }

    @Test
    void periodoPagamentoTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        PeriodoPagamento periodoPagamentoBack = getPeriodoPagamentoRandomSampleGenerator();

        calculoPlanoAssinatura.setPeriodoPagamento(periodoPagamentoBack);
        assertThat(calculoPlanoAssinatura.getPeriodoPagamento()).isEqualTo(periodoPagamentoBack);

        calculoPlanoAssinatura.periodoPagamento(null);
        assertThat(calculoPlanoAssinatura.getPeriodoPagamento()).isNull();
    }

    @Test
    void planoContaAzulTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        PlanoContaAzul planoContaAzulBack = getPlanoContaAzulRandomSampleGenerator();

        calculoPlanoAssinatura.setPlanoContaAzul(planoContaAzulBack);
        assertThat(calculoPlanoAssinatura.getPlanoContaAzul()).isEqualTo(planoContaAzulBack);

        calculoPlanoAssinatura.planoContaAzul(null);
        assertThat(calculoPlanoAssinatura.getPlanoContaAzul()).isNull();
    }

    @Test
    void planoContabilTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        calculoPlanoAssinatura.setPlanoContabil(planoContabilBack);
        assertThat(calculoPlanoAssinatura.getPlanoContabil()).isEqualTo(planoContabilBack);

        calculoPlanoAssinatura.planoContabil(null);
        assertThat(calculoPlanoAssinatura.getPlanoContabil()).isNull();
    }

    @Test
    void ramoTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        calculoPlanoAssinatura.setRamo(ramoBack);
        assertThat(calculoPlanoAssinatura.getRamo()).isEqualTo(ramoBack);

        calculoPlanoAssinatura.ramo(null);
        assertThat(calculoPlanoAssinatura.getRamo()).isNull();
    }

    @Test
    void tributacaoTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        Tributacao tributacaoBack = getTributacaoRandomSampleGenerator();

        calculoPlanoAssinatura.setTributacao(tributacaoBack);
        assertThat(calculoPlanoAssinatura.getTributacao()).isEqualTo(tributacaoBack);

        calculoPlanoAssinatura.tributacao(null);
        assertThat(calculoPlanoAssinatura.getTributacao()).isNull();
    }

    @Test
    void descontoPlanoContabilTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        DescontoPlanoContabil descontoPlanoContabilBack = getDescontoPlanoContabilRandomSampleGenerator();

        calculoPlanoAssinatura.setDescontoPlanoContabil(descontoPlanoContabilBack);
        assertThat(calculoPlanoAssinatura.getDescontoPlanoContabil()).isEqualTo(descontoPlanoContabilBack);

        calculoPlanoAssinatura.descontoPlanoContabil(null);
        assertThat(calculoPlanoAssinatura.getDescontoPlanoContabil()).isNull();
    }

    @Test
    void descontoPlanoContaAzulTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        DescontoPlanoContaAzul descontoPlanoContaAzulBack = getDescontoPlanoContaAzulRandomSampleGenerator();

        calculoPlanoAssinatura.setDescontoPlanoContaAzul(descontoPlanoContaAzulBack);
        assertThat(calculoPlanoAssinatura.getDescontoPlanoContaAzul()).isEqualTo(descontoPlanoContaAzulBack);

        calculoPlanoAssinatura.descontoPlanoContaAzul(null);
        assertThat(calculoPlanoAssinatura.getDescontoPlanoContaAzul()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        CalculoPlanoAssinatura calculoPlanoAssinatura = getCalculoPlanoAssinaturaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        calculoPlanoAssinatura.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(calculoPlanoAssinatura.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        calculoPlanoAssinatura.assinaturaEmpresa(null);
        assertThat(calculoPlanoAssinatura.getAssinaturaEmpresa()).isNull();
    }
}
