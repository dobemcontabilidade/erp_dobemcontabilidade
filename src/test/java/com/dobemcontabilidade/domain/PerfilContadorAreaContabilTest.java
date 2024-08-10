package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorAreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilContadorAreaContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilContadorAreaContabil.class);
        PerfilContadorAreaContabil perfilContadorAreaContabil1 = getPerfilContadorAreaContabilSample1();
        PerfilContadorAreaContabil perfilContadorAreaContabil2 = new PerfilContadorAreaContabil();
        assertThat(perfilContadorAreaContabil1).isNotEqualTo(perfilContadorAreaContabil2);

        perfilContadorAreaContabil2.setId(perfilContadorAreaContabil1.getId());
        assertThat(perfilContadorAreaContabil1).isEqualTo(perfilContadorAreaContabil2);

        perfilContadorAreaContabil2 = getPerfilContadorAreaContabilSample2();
        assertThat(perfilContadorAreaContabil1).isNotEqualTo(perfilContadorAreaContabil2);
    }

    @Test
    void areaContabilTest() {
        PerfilContadorAreaContabil perfilContadorAreaContabil = getPerfilContadorAreaContabilRandomSampleGenerator();
        AreaContabil areaContabilBack = getAreaContabilRandomSampleGenerator();

        perfilContadorAreaContabil.setAreaContabil(areaContabilBack);
        assertThat(perfilContadorAreaContabil.getAreaContabil()).isEqualTo(areaContabilBack);

        perfilContadorAreaContabil.areaContabil(null);
        assertThat(perfilContadorAreaContabil.getAreaContabil()).isNull();
    }

    @Test
    void perfilContadorTest() {
        PerfilContadorAreaContabil perfilContadorAreaContabil = getPerfilContadorAreaContabilRandomSampleGenerator();
        PerfilContador perfilContadorBack = getPerfilContadorRandomSampleGenerator();

        perfilContadorAreaContabil.setPerfilContador(perfilContadorBack);
        assertThat(perfilContadorAreaContabil.getPerfilContador()).isEqualTo(perfilContadorBack);

        perfilContadorAreaContabil.perfilContador(null);
        assertThat(perfilContadorAreaContabil.getPerfilContador()).isNull();
    }
}
