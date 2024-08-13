package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoRequeridoServicoContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoRequeridoServicoContabil.class);
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil1 = getAnexoRequeridoServicoContabilSample1();
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil2 = new AnexoRequeridoServicoContabil();
        assertThat(anexoRequeridoServicoContabil1).isNotEqualTo(anexoRequeridoServicoContabil2);

        anexoRequeridoServicoContabil2.setId(anexoRequeridoServicoContabil1.getId());
        assertThat(anexoRequeridoServicoContabil1).isEqualTo(anexoRequeridoServicoContabil2);

        anexoRequeridoServicoContabil2 = getAnexoRequeridoServicoContabilSample2();
        assertThat(anexoRequeridoServicoContabil1).isNotEqualTo(anexoRequeridoServicoContabil2);
    }

    @Test
    void servicoContabilTest() {
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil = getAnexoRequeridoServicoContabilRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        anexoRequeridoServicoContabil.setServicoContabil(servicoContabilBack);
        assertThat(anexoRequeridoServicoContabil.getServicoContabil()).isEqualTo(servicoContabilBack);

        anexoRequeridoServicoContabil.servicoContabil(null);
        assertThat(anexoRequeridoServicoContabil.getServicoContabil()).isNull();
    }

    @Test
    void anexoRequeridoTest() {
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil = getAnexoRequeridoServicoContabilRandomSampleGenerator();
        AnexoRequerido anexoRequeridoBack = getAnexoRequeridoRandomSampleGenerator();

        anexoRequeridoServicoContabil.setAnexoRequerido(anexoRequeridoBack);
        assertThat(anexoRequeridoServicoContabil.getAnexoRequerido()).isEqualTo(anexoRequeridoBack);

        anexoRequeridoServicoContabil.anexoRequerido(null);
        assertThat(anexoRequeridoServicoContabil.getAnexoRequerido()).isNull();
    }
}
