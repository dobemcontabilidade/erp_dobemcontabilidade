package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CriterioAvaliacaoAtorTestSamples.*;
import static com.dobemcontabilidade.domain.CriterioAvaliacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CriterioAvaliacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CriterioAvaliacao.class);
        CriterioAvaliacao criterioAvaliacao1 = getCriterioAvaliacaoSample1();
        CriterioAvaliacao criterioAvaliacao2 = new CriterioAvaliacao();
        assertThat(criterioAvaliacao1).isNotEqualTo(criterioAvaliacao2);

        criterioAvaliacao2.setId(criterioAvaliacao1.getId());
        assertThat(criterioAvaliacao1).isEqualTo(criterioAvaliacao2);

        criterioAvaliacao2 = getCriterioAvaliacaoSample2();
        assertThat(criterioAvaliacao1).isNotEqualTo(criterioAvaliacao2);
    }

    @Test
    void criterioAvaliacaoAtorTest() {
        CriterioAvaliacao criterioAvaliacao = getCriterioAvaliacaoRandomSampleGenerator();
        CriterioAvaliacaoAtor criterioAvaliacaoAtorBack = getCriterioAvaliacaoAtorRandomSampleGenerator();

        criterioAvaliacao.addCriterioAvaliacaoAtor(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacao.getCriterioAvaliacaoAtors()).containsOnly(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getCriterioAvaliacao()).isEqualTo(criterioAvaliacao);

        criterioAvaliacao.removeCriterioAvaliacaoAtor(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacao.getCriterioAvaliacaoAtors()).doesNotContain(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getCriterioAvaliacao()).isNull();

        criterioAvaliacao.criterioAvaliacaoAtors(new HashSet<>(Set.of(criterioAvaliacaoAtorBack)));
        assertThat(criterioAvaliacao.getCriterioAvaliacaoAtors()).containsOnly(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getCriterioAvaliacao()).isEqualTo(criterioAvaliacao);

        criterioAvaliacao.setCriterioAvaliacaoAtors(new HashSet<>());
        assertThat(criterioAvaliacao.getCriterioAvaliacaoAtors()).doesNotContain(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getCriterioAvaliacao()).isNull();
    }
}
