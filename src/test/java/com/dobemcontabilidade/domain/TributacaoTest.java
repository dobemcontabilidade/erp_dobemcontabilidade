package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalTributacaoTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
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
    void empresaTest() {
        Tributacao tributacao = getTributacaoRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        tributacao.addEmpresa(empresaBack);
        assertThat(tributacao.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getTributacao()).isEqualTo(tributacao);

        tributacao.removeEmpresa(empresaBack);
        assertThat(tributacao.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getTributacao()).isNull();

        tributacao.empresas(new HashSet<>(Set.of(empresaBack)));
        assertThat(tributacao.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getTributacao()).isEqualTo(tributacao);

        tributacao.setEmpresas(new HashSet<>());
        assertThat(tributacao.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getTributacao()).isNull();
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
