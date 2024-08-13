package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AtorAvaliadoTestSamples.*;
import static com.dobemcontabilidade.domain.CriterioAvaliacaoAtorTestSamples.*;
import static com.dobemcontabilidade.domain.CriterioAvaliacaoTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackContadorParaUsuarioTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackUsuarioParaContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CriterioAvaliacaoAtorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CriterioAvaliacaoAtor.class);
        CriterioAvaliacaoAtor criterioAvaliacaoAtor1 = getCriterioAvaliacaoAtorSample1();
        CriterioAvaliacaoAtor criterioAvaliacaoAtor2 = new CriterioAvaliacaoAtor();
        assertThat(criterioAvaliacaoAtor1).isNotEqualTo(criterioAvaliacaoAtor2);

        criterioAvaliacaoAtor2.setId(criterioAvaliacaoAtor1.getId());
        assertThat(criterioAvaliacaoAtor1).isEqualTo(criterioAvaliacaoAtor2);

        criterioAvaliacaoAtor2 = getCriterioAvaliacaoAtorSample2();
        assertThat(criterioAvaliacaoAtor1).isNotEqualTo(criterioAvaliacaoAtor2);
    }

    @Test
    void feedBackUsuarioParaContadorTest() {
        CriterioAvaliacaoAtor criterioAvaliacaoAtor = getCriterioAvaliacaoAtorRandomSampleGenerator();
        FeedBackUsuarioParaContador feedBackUsuarioParaContadorBack = getFeedBackUsuarioParaContadorRandomSampleGenerator();

        criterioAvaliacaoAtor.addFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(criterioAvaliacaoAtor.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getCriterioAvaliacaoAtor()).isEqualTo(criterioAvaliacaoAtor);

        criterioAvaliacaoAtor.removeFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(criterioAvaliacaoAtor.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getCriterioAvaliacaoAtor()).isNull();

        criterioAvaliacaoAtor.feedBackUsuarioParaContadors(new HashSet<>(Set.of(feedBackUsuarioParaContadorBack)));
        assertThat(criterioAvaliacaoAtor.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getCriterioAvaliacaoAtor()).isEqualTo(criterioAvaliacaoAtor);

        criterioAvaliacaoAtor.setFeedBackUsuarioParaContadors(new HashSet<>());
        assertThat(criterioAvaliacaoAtor.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getCriterioAvaliacaoAtor()).isNull();
    }

    @Test
    void feedBackContadorParaUsuarioTest() {
        CriterioAvaliacaoAtor criterioAvaliacaoAtor = getCriterioAvaliacaoAtorRandomSampleGenerator();
        FeedBackContadorParaUsuario feedBackContadorParaUsuarioBack = getFeedBackContadorParaUsuarioRandomSampleGenerator();

        criterioAvaliacaoAtor.addFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(criterioAvaliacaoAtor.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getCriterioAvaliacaoAtor()).isEqualTo(criterioAvaliacaoAtor);

        criterioAvaliacaoAtor.removeFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(criterioAvaliacaoAtor.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getCriterioAvaliacaoAtor()).isNull();

        criterioAvaliacaoAtor.feedBackContadorParaUsuarios(new HashSet<>(Set.of(feedBackContadorParaUsuarioBack)));
        assertThat(criterioAvaliacaoAtor.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getCriterioAvaliacaoAtor()).isEqualTo(criterioAvaliacaoAtor);

        criterioAvaliacaoAtor.setFeedBackContadorParaUsuarios(new HashSet<>());
        assertThat(criterioAvaliacaoAtor.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getCriterioAvaliacaoAtor()).isNull();
    }

    @Test
    void criterioAvaliacaoTest() {
        CriterioAvaliacaoAtor criterioAvaliacaoAtor = getCriterioAvaliacaoAtorRandomSampleGenerator();
        CriterioAvaliacao criterioAvaliacaoBack = getCriterioAvaliacaoRandomSampleGenerator();

        criterioAvaliacaoAtor.setCriterioAvaliacao(criterioAvaliacaoBack);
        assertThat(criterioAvaliacaoAtor.getCriterioAvaliacao()).isEqualTo(criterioAvaliacaoBack);

        criterioAvaliacaoAtor.criterioAvaliacao(null);
        assertThat(criterioAvaliacaoAtor.getCriterioAvaliacao()).isNull();
    }

    @Test
    void atorAvaliadoTest() {
        CriterioAvaliacaoAtor criterioAvaliacaoAtor = getCriterioAvaliacaoAtorRandomSampleGenerator();
        AtorAvaliado atorAvaliadoBack = getAtorAvaliadoRandomSampleGenerator();

        criterioAvaliacaoAtor.setAtorAvaliado(atorAvaliadoBack);
        assertThat(criterioAvaliacaoAtor.getAtorAvaliado()).isEqualTo(atorAvaliadoBack);

        criterioAvaliacaoAtor.atorAvaliado(null);
        assertThat(criterioAvaliacaoAtor.getAtorAvaliado()).isNull();
    }
}
