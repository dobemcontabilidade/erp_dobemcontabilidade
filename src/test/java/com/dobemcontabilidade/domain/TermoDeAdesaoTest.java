package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.TermoAdesaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.TermoDeAdesaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TermoDeAdesaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoDeAdesao.class);
        TermoDeAdesao termoDeAdesao1 = getTermoDeAdesaoSample1();
        TermoDeAdesao termoDeAdesao2 = new TermoDeAdesao();
        assertThat(termoDeAdesao1).isNotEqualTo(termoDeAdesao2);

        termoDeAdesao2.setId(termoDeAdesao1.getId());
        assertThat(termoDeAdesao1).isEqualTo(termoDeAdesao2);

        termoDeAdesao2 = getTermoDeAdesaoSample2();
        assertThat(termoDeAdesao1).isNotEqualTo(termoDeAdesao2);
    }

    @Test
    void termoAdesaoContadorTest() {
        TermoDeAdesao termoDeAdesao = getTermoDeAdesaoRandomSampleGenerator();
        TermoAdesaoContador termoAdesaoContadorBack = getTermoAdesaoContadorRandomSampleGenerator();

        termoDeAdesao.addTermoAdesaoContador(termoAdesaoContadorBack);
        assertThat(termoDeAdesao.getTermoAdesaoContadors()).containsOnly(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getTermoDeAdesao()).isEqualTo(termoDeAdesao);

        termoDeAdesao.removeTermoAdesaoContador(termoAdesaoContadorBack);
        assertThat(termoDeAdesao.getTermoAdesaoContadors()).doesNotContain(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getTermoDeAdesao()).isNull();

        termoDeAdesao.termoAdesaoContadors(new HashSet<>(Set.of(termoAdesaoContadorBack)));
        assertThat(termoDeAdesao.getTermoAdesaoContadors()).containsOnly(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getTermoDeAdesao()).isEqualTo(termoDeAdesao);

        termoDeAdesao.setTermoAdesaoContadors(new HashSet<>());
        assertThat(termoDeAdesao.getTermoAdesaoContadors()).doesNotContain(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getTermoDeAdesao()).isNull();
    }
}
