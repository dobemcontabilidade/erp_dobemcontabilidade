package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ClasseCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.DivisaoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GrupoCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoCnae.class);
        GrupoCnae grupoCnae1 = getGrupoCnaeSample1();
        GrupoCnae grupoCnae2 = new GrupoCnae();
        assertThat(grupoCnae1).isNotEqualTo(grupoCnae2);

        grupoCnae2.setId(grupoCnae1.getId());
        assertThat(grupoCnae1).isEqualTo(grupoCnae2);

        grupoCnae2 = getGrupoCnaeSample2();
        assertThat(grupoCnae1).isNotEqualTo(grupoCnae2);
    }

    @Test
    void classeCnaeTest() {
        GrupoCnae grupoCnae = getGrupoCnaeRandomSampleGenerator();
        ClasseCnae classeCnaeBack = getClasseCnaeRandomSampleGenerator();

        grupoCnae.addClasseCnae(classeCnaeBack);
        assertThat(grupoCnae.getClasseCnaes()).containsOnly(classeCnaeBack);
        assertThat(classeCnaeBack.getGrupo()).isEqualTo(grupoCnae);

        grupoCnae.removeClasseCnae(classeCnaeBack);
        assertThat(grupoCnae.getClasseCnaes()).doesNotContain(classeCnaeBack);
        assertThat(classeCnaeBack.getGrupo()).isNull();

        grupoCnae.classeCnaes(new HashSet<>(Set.of(classeCnaeBack)));
        assertThat(grupoCnae.getClasseCnaes()).containsOnly(classeCnaeBack);
        assertThat(classeCnaeBack.getGrupo()).isEqualTo(grupoCnae);

        grupoCnae.setClasseCnaes(new HashSet<>());
        assertThat(grupoCnae.getClasseCnaes()).doesNotContain(classeCnaeBack);
        assertThat(classeCnaeBack.getGrupo()).isNull();
    }

    @Test
    void divisaoTest() {
        GrupoCnae grupoCnae = getGrupoCnaeRandomSampleGenerator();
        DivisaoCnae divisaoCnaeBack = getDivisaoCnaeRandomSampleGenerator();

        grupoCnae.setDivisao(divisaoCnaeBack);
        assertThat(grupoCnae.getDivisao()).isEqualTo(divisaoCnaeBack);

        grupoCnae.divisao(null);
        assertThat(grupoCnae.getDivisao()).isNull();
    }
}
