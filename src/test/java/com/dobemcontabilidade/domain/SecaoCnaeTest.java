package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DivisaoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SecaoCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SecaoCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecaoCnae.class);
        SecaoCnae secaoCnae1 = getSecaoCnaeSample1();
        SecaoCnae secaoCnae2 = new SecaoCnae();
        assertThat(secaoCnae1).isNotEqualTo(secaoCnae2);

        secaoCnae2.setId(secaoCnae1.getId());
        assertThat(secaoCnae1).isEqualTo(secaoCnae2);

        secaoCnae2 = getSecaoCnaeSample2();
        assertThat(secaoCnae1).isNotEqualTo(secaoCnae2);
    }

    @Test
    void divisaoCnaeTest() {
        SecaoCnae secaoCnae = getSecaoCnaeRandomSampleGenerator();
        DivisaoCnae divisaoCnaeBack = getDivisaoCnaeRandomSampleGenerator();

        secaoCnae.addDivisaoCnae(divisaoCnaeBack);
        assertThat(secaoCnae.getDivisaoCnaes()).containsOnly(divisaoCnaeBack);
        assertThat(divisaoCnaeBack.getSecao()).isEqualTo(secaoCnae);

        secaoCnae.removeDivisaoCnae(divisaoCnaeBack);
        assertThat(secaoCnae.getDivisaoCnaes()).doesNotContain(divisaoCnaeBack);
        assertThat(divisaoCnaeBack.getSecao()).isNull();

        secaoCnae.divisaoCnaes(new HashSet<>(Set.of(divisaoCnaeBack)));
        assertThat(secaoCnae.getDivisaoCnaes()).containsOnly(divisaoCnaeBack);
        assertThat(divisaoCnaeBack.getSecao()).isEqualTo(secaoCnae);

        secaoCnae.setDivisaoCnaes(new HashSet<>());
        assertThat(secaoCnae.getDivisaoCnaes()).doesNotContain(divisaoCnaeBack);
        assertThat(divisaoCnaeBack.getSecao()).isNull();
    }
}
