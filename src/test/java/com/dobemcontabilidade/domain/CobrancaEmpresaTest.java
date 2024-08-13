package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CobrancaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FormaDePagamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CobrancaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CobrancaEmpresa.class);
        CobrancaEmpresa cobrancaEmpresa1 = getCobrancaEmpresaSample1();
        CobrancaEmpresa cobrancaEmpresa2 = new CobrancaEmpresa();
        assertThat(cobrancaEmpresa1).isNotEqualTo(cobrancaEmpresa2);

        cobrancaEmpresa2.setId(cobrancaEmpresa1.getId());
        assertThat(cobrancaEmpresa1).isEqualTo(cobrancaEmpresa2);

        cobrancaEmpresa2 = getCobrancaEmpresaSample2();
        assertThat(cobrancaEmpresa1).isNotEqualTo(cobrancaEmpresa2);
    }

    @Test
    void assinaturaEmpresaTest() {
        CobrancaEmpresa cobrancaEmpresa = getCobrancaEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        cobrancaEmpresa.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(cobrancaEmpresa.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        cobrancaEmpresa.assinaturaEmpresa(null);
        assertThat(cobrancaEmpresa.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void formaDePagamentoTest() {
        CobrancaEmpresa cobrancaEmpresa = getCobrancaEmpresaRandomSampleGenerator();
        FormaDePagamento formaDePagamentoBack = getFormaDePagamentoRandomSampleGenerator();

        cobrancaEmpresa.setFormaDePagamento(formaDePagamentoBack);
        assertThat(cobrancaEmpresa.getFormaDePagamento()).isEqualTo(formaDePagamentoBack);

        cobrancaEmpresa.formaDePagamento(null);
        assertThat(cobrancaEmpresa.getFormaDePagamento()).isNull();
    }
}
