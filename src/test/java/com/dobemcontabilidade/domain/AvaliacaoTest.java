package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AvaliacaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AvaliacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AvaliacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avaliacao.class);
        Avaliacao avaliacao1 = getAvaliacaoSample1();
        Avaliacao avaliacao2 = new Avaliacao();
        assertThat(avaliacao1).isNotEqualTo(avaliacao2);

        avaliacao2.setId(avaliacao1.getId());
        assertThat(avaliacao1).isEqualTo(avaliacao2);

        avaliacao2 = getAvaliacaoSample2();
        assertThat(avaliacao1).isNotEqualTo(avaliacao2);
    }

    @Test
    void avaliacaoContadorTest() {
        Avaliacao avaliacao = getAvaliacaoRandomSampleGenerator();
        AvaliacaoContador avaliacaoContadorBack = getAvaliacaoContadorRandomSampleGenerator();

        avaliacao.addAvaliacaoContador(avaliacaoContadorBack);
        assertThat(avaliacao.getAvaliacaoContadors()).containsOnly(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getAvaliacao()).isEqualTo(avaliacao);

        avaliacao.removeAvaliacaoContador(avaliacaoContadorBack);
        assertThat(avaliacao.getAvaliacaoContadors()).doesNotContain(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getAvaliacao()).isNull();

        avaliacao.avaliacaoContadors(new HashSet<>(Set.of(avaliacaoContadorBack)));
        assertThat(avaliacao.getAvaliacaoContadors()).containsOnly(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getAvaliacao()).isEqualTo(avaliacao);

        avaliacao.setAvaliacaoContadors(new HashSet<>());
        assertThat(avaliacao.getAvaliacaoContadors()).doesNotContain(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getAvaliacao()).isNull();
    }
}
