package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalTributacaoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TributacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tributacao.class);
        Tributacao tributacao1 = getTributacaoSample1();
        Tributacao tributacao2 = new Tributacao();
        assertThat(tributacao1).isNotEqualTo(tributacao2);

        tributacao2.setId(tributacao1.getId());
        assertThat(tributacao1).isEqualTo(tributacao2);

        tributacao2 = getTributacaoSample2();
        assertThat(tributacao1).isNotEqualTo(tributacao2);
    }

    @Test
    void anexoRequeridoEmpresaTest() {
        Tributacao tributacao = getTributacaoRandomSampleGenerator();
        AnexoRequeridoEmpresa anexoRequeridoEmpresaBack = getAnexoRequeridoEmpresaRandomSampleGenerator();

        tributacao.addAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(tributacao.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getTributacao()).isEqualTo(tributacao);

        tributacao.removeAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(tributacao.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getTributacao()).isNull();

        tributacao.anexoRequeridoEmpresas(new HashSet<>(Set.of(anexoRequeridoEmpresaBack)));
        assertThat(tributacao.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getTributacao()).isEqualTo(tributacao);

        tributacao.setAnexoRequeridoEmpresas(new HashSet<>());
        assertThat(tributacao.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getTributacao()).isNull();
    }

    @Test
    void empresaModeloTest() {
        Tributacao tributacao = getTributacaoRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        tributacao.addEmpresaModelo(empresaModeloBack);
        assertThat(tributacao.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getTributacao()).isEqualTo(tributacao);

        tributacao.removeEmpresaModelo(empresaModeloBack);
        assertThat(tributacao.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getTributacao()).isNull();

        tributacao.empresaModelos(new HashSet<>(Set.of(empresaModeloBack)));
        assertThat(tributacao.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getTributacao()).isEqualTo(tributacao);

        tributacao.setEmpresaModelos(new HashSet<>());
        assertThat(tributacao.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getTributacao()).isNull();
    }

    @Test
    void calculoPlanoAssinaturaTest() {
        Tributacao tributacao = getTributacaoRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        tributacao.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(tributacao.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getTributacao()).isEqualTo(tributacao);

        tributacao.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(tributacao.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getTributacao()).isNull();

        tributacao.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(tributacao.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getTributacao()).isEqualTo(tributacao);

        tributacao.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(tributacao.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getTributacao()).isNull();
    }

    @Test
    void adicionalTributacaoTest() {
        Tributacao tributacao = getTributacaoRandomSampleGenerator();
        AdicionalTributacao adicionalTributacaoBack = getAdicionalTributacaoRandomSampleGenerator();

        tributacao.addAdicionalTributacao(adicionalTributacaoBack);
        assertThat(tributacao.getAdicionalTributacaos()).containsOnly(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getTributacao()).isEqualTo(tributacao);

        tributacao.removeAdicionalTributacao(adicionalTributacaoBack);
        assertThat(tributacao.getAdicionalTributacaos()).doesNotContain(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getTributacao()).isNull();

        tributacao.adicionalTributacaos(new HashSet<>(Set.of(adicionalTributacaoBack)));
        assertThat(tributacao.getAdicionalTributacaos()).containsOnly(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getTributacao()).isEqualTo(tributacao);

        tributacao.setAdicionalTributacaos(new HashSet<>());
        assertThat(tributacao.getAdicionalTributacaos()).doesNotContain(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getTributacao()).isNull();
    }
}
