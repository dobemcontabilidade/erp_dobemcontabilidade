package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FormaDePagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
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
    void calculoPlanoAssinaturaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        assinaturaEmpresa.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void pagamentoTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        Pagamento pagamentoBack = getPagamentoRandomSampleGenerator();

        assinaturaEmpresa.addPagamento(pagamentoBack);
        assertThat(assinaturaEmpresa.getPagamentos()).containsOnly(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removePagamento(pagamentoBack);
        assertThat(assinaturaEmpresa.getPagamentos()).doesNotContain(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.pagamentos(new HashSet<>(Set.of(pagamentoBack)));
        assertThat(assinaturaEmpresa.getPagamentos()).containsOnly(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setPagamentos(new HashSet<>());
        assertThat(assinaturaEmpresa.getPagamentos()).doesNotContain(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isNull();
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
    void planoContabilTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        assinaturaEmpresa.setPlanoContabil(planoContabilBack);
        assertThat(assinaturaEmpresa.getPlanoContabil()).isEqualTo(planoContabilBack);

        assinaturaEmpresa.planoContabil(null);
        assertThat(assinaturaEmpresa.getPlanoContabil()).isNull();
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
