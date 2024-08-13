package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DivisaoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SecaoCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DivisaoCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DivisaoCnae.class);
        DivisaoCnae divisaoCnae1 = getDivisaoCnaeSample1();
        DivisaoCnae divisaoCnae2 = new DivisaoCnae();
        assertThat(divisaoCnae1).isNotEqualTo(divisaoCnae2);

        divisaoCnae2.setId(divisaoCnae1.getId());
        assertThat(divisaoCnae1).isEqualTo(divisaoCnae2);

        divisaoCnae2 = getDivisaoCnaeSample2();
        assertThat(divisaoCnae1).isNotEqualTo(divisaoCnae2);
    }

    @Test
    void grupoCnaeTest() {
        DivisaoCnae divisaoCnae = getDivisaoCnaeRandomSampleGenerator();
        GrupoCnae grupoCnaeBack = getGrupoCnaeRandomSampleGenerator();

        divisaoCnae.addGrupoCnae(grupoCnaeBack);
        assertThat(divisaoCnae.getGrupoCnaes()).containsOnly(grupoCnaeBack);
        assertThat(grupoCnaeBack.getDivisao()).isEqualTo(divisaoCnae);

        divisaoCnae.removeGrupoCnae(grupoCnaeBack);
        assertThat(divisaoCnae.getGrupoCnaes()).doesNotContain(grupoCnaeBack);
        assertThat(grupoCnaeBack.getDivisao()).isNull();

        divisaoCnae.grupoCnaes(new HashSet<>(Set.of(grupoCnaeBack)));
        assertThat(divisaoCnae.getGrupoCnaes()).containsOnly(grupoCnaeBack);
        assertThat(grupoCnaeBack.getDivisao()).isEqualTo(divisaoCnae);

        divisaoCnae.setGrupoCnaes(new HashSet<>());
        assertThat(divisaoCnae.getGrupoCnaes()).doesNotContain(grupoCnaeBack);
        assertThat(grupoCnaeBack.getDivisao()).isNull();
    }

    @Test
    void secaoTest() {
        DivisaoCnae divisaoCnae = getDivisaoCnaeRandomSampleGenerator();
        SecaoCnae secaoCnaeBack = getSecaoCnaeRandomSampleGenerator();

        divisaoCnae.setSecao(secaoCnaeBack);
        assertThat(divisaoCnae.getSecao()).isEqualTo(secaoCnaeBack);

        divisaoCnae.secao(null);
        assertThat(divisaoCnae.getSecao()).isNull();
    }
}
