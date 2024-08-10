package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorAreaContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AreaContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabil.class);
        AreaContabil areaContabil1 = getAreaContabilSample1();
        AreaContabil areaContabil2 = new AreaContabil();
        assertThat(areaContabil1).isNotEqualTo(areaContabil2);

        areaContabil2.setId(areaContabil1.getId());
        assertThat(areaContabil1).isEqualTo(areaContabil2);

        areaContabil2 = getAreaContabilSample2();
        assertThat(areaContabil1).isNotEqualTo(areaContabil2);
    }

    @Test
    void perfilContadorAreaContabilTest() {
        AreaContabil areaContabil = getAreaContabilRandomSampleGenerator();
        PerfilContadorAreaContabil perfilContadorAreaContabilBack = getPerfilContadorAreaContabilRandomSampleGenerator();

        areaContabil.addPerfilContadorAreaContabil(perfilContadorAreaContabilBack);
        assertThat(areaContabil.getPerfilContadorAreaContabils()).containsOnly(perfilContadorAreaContabilBack);
        assertThat(perfilContadorAreaContabilBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.removePerfilContadorAreaContabil(perfilContadorAreaContabilBack);
        assertThat(areaContabil.getPerfilContadorAreaContabils()).doesNotContain(perfilContadorAreaContabilBack);
        assertThat(perfilContadorAreaContabilBack.getAreaContabil()).isNull();

        areaContabil.perfilContadorAreaContabils(new HashSet<>(Set.of(perfilContadorAreaContabilBack)));
        assertThat(areaContabil.getPerfilContadorAreaContabils()).containsOnly(perfilContadorAreaContabilBack);
        assertThat(perfilContadorAreaContabilBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.setPerfilContadorAreaContabils(new HashSet<>());
        assertThat(areaContabil.getPerfilContadorAreaContabils()).doesNotContain(perfilContadorAreaContabilBack);
        assertThat(perfilContadorAreaContabilBack.getAreaContabil()).isNull();
    }

    @Test
    void areaContabilContadorTest() {
        AreaContabil areaContabil = getAreaContabilRandomSampleGenerator();
        AreaContabilContador areaContabilContadorBack = getAreaContabilContadorRandomSampleGenerator();

        areaContabil.addAreaContabilContador(areaContabilContadorBack);
        assertThat(areaContabil.getAreaContabilContadors()).containsOnly(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.removeAreaContabilContador(areaContabilContadorBack);
        assertThat(areaContabil.getAreaContabilContadors()).doesNotContain(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isNull();

        areaContabil.areaContabilContadors(new HashSet<>(Set.of(areaContabilContadorBack)));
        assertThat(areaContabil.getAreaContabilContadors()).containsOnly(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.setAreaContabilContadors(new HashSet<>());
        assertThat(areaContabil.getAreaContabilContadors()).doesNotContain(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isNull();
    }
}
