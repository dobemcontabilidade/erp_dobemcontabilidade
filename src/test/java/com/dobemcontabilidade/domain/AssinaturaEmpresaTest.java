package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FormaDePagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AssinaturaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssinaturaEmpresa.class);
        AssinaturaEmpresa assinaturaEmpresa1 = getAssinaturaEmpresaSample1();
        AssinaturaEmpresa assinaturaEmpresa2 = new AssinaturaEmpresa();
        assertThat(assinaturaEmpresa1).isNotEqualTo(assinaturaEmpresa2);

        assinaturaEmpresa2.setId(assinaturaEmpresa1.getId());
        assertThat(assinaturaEmpresa1).isEqualTo(assinaturaEmpresa2);

        assinaturaEmpresa2 = getAssinaturaEmpresaSample2();
        assertThat(assinaturaEmpresa1).isNotEqualTo(assinaturaEmpresa2);
    }

    @Test
    void termoContratoAssinaturaEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresaBack = getTermoContratoAssinaturaEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addTermoContratoAssinaturaEmpresa(termoContratoAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getTermoContratoAssinaturaEmpresas()).containsOnly(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeTermoContratoAssinaturaEmpresa(termoContratoAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getTermoContratoAssinaturaEmpresas()).doesNotContain(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getEmpresa()).isNull();

        assinaturaEmpresa.termoContratoAssinaturaEmpresas(new HashSet<>(Set.of(termoContratoAssinaturaEmpresaBack)));
        assertThat(assinaturaEmpresa.getTermoContratoAssinaturaEmpresas()).containsOnly(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setTermoContratoAssinaturaEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getTermoContratoAssinaturaEmpresas()).doesNotContain(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void periodoPagamentoTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        PeriodoPagamento periodoPagamentoBack = getPeriodoPagamentoRandomSampleGenerator();

        assinaturaEmpresa.setPeriodoPagamento(periodoPagamentoBack);
        assertThat(assinaturaEmpresa.getPeriodoPagamento()).isEqualTo(periodoPagamentoBack);

        assinaturaEmpresa.periodoPagamento(null);
        assertThat(assinaturaEmpresa.getPeriodoPagamento()).isNull();
    }

    @Test
    void formaDePagamentoTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        FormaDePagamento formaDePagamentoBack = getFormaDePagamentoRandomSampleGenerator();

        assinaturaEmpresa.setFormaDePagamento(formaDePagamentoBack);
        assertThat(assinaturaEmpresa.getFormaDePagamento()).isEqualTo(formaDePagamentoBack);

        assinaturaEmpresa.formaDePagamento(null);
        assertThat(assinaturaEmpresa.getFormaDePagamento()).isNull();
    }

    @Test
    void empresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        assinaturaEmpresa.setEmpresa(empresaBack);
        assertThat(assinaturaEmpresa.getEmpresa()).isEqualTo(empresaBack);

        assinaturaEmpresa.empresa(null);
        assertThat(assinaturaEmpresa.getEmpresa()).isNull();
    }
}
