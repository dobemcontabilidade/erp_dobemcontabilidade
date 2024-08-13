package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AtorAvaliadoTestSamples.*;
import static com.dobemcontabilidade.domain.CriterioAvaliacaoAtorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AtorAvaliadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtorAvaliado.class);
        AtorAvaliado atorAvaliado1 = getAtorAvaliadoSample1();
        AtorAvaliado atorAvaliado2 = new AtorAvaliado();
        assertThat(atorAvaliado1).isNotEqualTo(atorAvaliado2);

        atorAvaliado2.setId(atorAvaliado1.getId());
        assertThat(atorAvaliado1).isEqualTo(atorAvaliado2);

        atorAvaliado2 = getAtorAvaliadoSample2();
        assertThat(atorAvaliado1).isNotEqualTo(atorAvaliado2);
    }

    @Test
    void criterioAvaliacaoAtorTest() {
        AtorAvaliado atorAvaliado = getAtorAvaliadoRandomSampleGenerator();
        CriterioAvaliacaoAtor criterioAvaliacaoAtorBack = getCriterioAvaliacaoAtorRandomSampleGenerator();

        atorAvaliado.addCriterioAvaliacaoAtor(criterioAvaliacaoAtorBack);
        assertThat(atorAvaliado.getCriterioAvaliacaoAtors()).containsOnly(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getAtorAvaliado()).isEqualTo(atorAvaliado);

        atorAvaliado.removeCriterioAvaliacaoAtor(criterioAvaliacaoAtorBack);
        assertThat(atorAvaliado.getCriterioAvaliacaoAtors()).doesNotContain(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getAtorAvaliado()).isNull();

        atorAvaliado.criterioAvaliacaoAtors(new HashSet<>(Set.of(criterioAvaliacaoAtorBack)));
        assertThat(atorAvaliado.getCriterioAvaliacaoAtors()).containsOnly(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getAtorAvaliado()).isEqualTo(atorAvaliado);

        atorAvaliado.setCriterioAvaliacaoAtors(new HashSet<>());
        assertThat(atorAvaliado.getCriterioAvaliacaoAtors()).doesNotContain(criterioAvaliacaoAtorBack);
        assertThat(criterioAvaliacaoAtorBack.getAtorAvaliado()).isNull();
    }
}
