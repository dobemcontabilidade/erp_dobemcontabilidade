package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalTributacaoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalTributacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalTributacao.class);
        AdicionalTributacao adicionalTributacao1 = getAdicionalTributacaoSample1();
        AdicionalTributacao adicionalTributacao2 = new AdicionalTributacao();
        assertThat(adicionalTributacao1).isNotEqualTo(adicionalTributacao2);

        adicionalTributacao2.setId(adicionalTributacao1.getId());
        assertThat(adicionalTributacao1).isEqualTo(adicionalTributacao2);

        adicionalTributacao2 = getAdicionalTributacaoSample2();
        assertThat(adicionalTributacao1).isNotEqualTo(adicionalTributacao2);
    }

    @Test
    void tributacaoTest() {
        AdicionalTributacao adicionalTributacao = getAdicionalTributacaoRandomSampleGenerator();
        Tributacao tributacaoBack = getTributacaoRandomSampleGenerator();

        adicionalTributacao.setTributacao(tributacaoBack);
        assertThat(adicionalTributacao.getTributacao()).isEqualTo(tributacaoBack);

        adicionalTributacao.tributacao(null);
        assertThat(adicionalTributacao.getTributacao()).isNull();
    }

    @Test
    void planoContabilTest() {
        AdicionalTributacao adicionalTributacao = getAdicionalTributacaoRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        adicionalTributacao.setPlanoContabil(planoContabilBack);
        assertThat(adicionalTributacao.getPlanoContabil()).isEqualTo(planoContabilBack);

        adicionalTributacao.planoContabil(null);
        assertThat(adicionalTributacao.getPlanoContabil()).isNull();
    }
}
