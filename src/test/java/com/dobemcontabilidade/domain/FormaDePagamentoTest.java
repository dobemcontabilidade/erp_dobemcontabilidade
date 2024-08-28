package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FormaDePagamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FormaDePagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaDePagamento.class);
        FormaDePagamento formaDePagamento1 = getFormaDePagamentoSample1();
        FormaDePagamento formaDePagamento2 = new FormaDePagamento();
        assertThat(formaDePagamento1).isNotEqualTo(formaDePagamento2);

        formaDePagamento2.setId(formaDePagamento1.getId());
        assertThat(formaDePagamento1).isEqualTo(formaDePagamento2);

        formaDePagamento2 = getFormaDePagamentoSample2();
        assertThat(formaDePagamento1).isNotEqualTo(formaDePagamento2);
    }

    @Test
    void assinaturaEmpresaTest() {
        FormaDePagamento formaDePagamento = getFormaDePagamentoRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        formaDePagamento.addAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(formaDePagamento.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getFormaDePagamento()).isEqualTo(formaDePagamento);

        formaDePagamento.removeAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(formaDePagamento.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getFormaDePagamento()).isNull();

        formaDePagamento.assinaturaEmpresas(new HashSet<>(Set.of(assinaturaEmpresaBack)));
        assertThat(formaDePagamento.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getFormaDePagamento()).isEqualTo(formaDePagamento);

        formaDePagamento.setAssinaturaEmpresas(new HashSet<>());
        assertThat(formaDePagamento.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getFormaDePagamento()).isNull();
    }
}
