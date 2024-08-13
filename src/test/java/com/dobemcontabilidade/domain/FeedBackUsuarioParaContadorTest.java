package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CriterioAvaliacaoAtorTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackUsuarioParaContadorTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedBackUsuarioParaContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBackUsuarioParaContador.class);
        FeedBackUsuarioParaContador feedBackUsuarioParaContador1 = getFeedBackUsuarioParaContadorSample1();
        FeedBackUsuarioParaContador feedBackUsuarioParaContador2 = new FeedBackUsuarioParaContador();
        assertThat(feedBackUsuarioParaContador1).isNotEqualTo(feedBackUsuarioParaContador2);

        feedBackUsuarioParaContador2.setId(feedBackUsuarioParaContador1.getId());
        assertThat(feedBackUsuarioParaContador1).isEqualTo(feedBackUsuarioParaContador2);

        feedBackUsuarioParaContador2 = getFeedBackUsuarioParaContadorSample2();
        assertThat(feedBackUsuarioParaContador1).isNotEqualTo(feedBackUsuarioParaContador2);
    }

    @Test
    void usuarioEmpresaTest() {
        FeedBackUsuarioParaContador feedBackUsuarioParaContador = getFeedBackUsuarioParaContadorRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        feedBackUsuarioParaContador.setUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(feedBackUsuarioParaContador.getUsuarioEmpresa()).isEqualTo(usuarioEmpresaBack);

        feedBackUsuarioParaContador.usuarioEmpresa(null);
        assertThat(feedBackUsuarioParaContador.getUsuarioEmpresa()).isNull();
    }

    @Test
    void usuarioContadorTest() {
        FeedBackUsuarioParaContador feedBackUsuarioParaContador = getFeedBackUsuarioParaContadorRandomSampleGenerator();
        UsuarioContador usuarioContadorBack = getUsuarioContadorRandomSampleGenerator();

        feedBackUsuarioParaContador.setUsuarioContador(usuarioContadorBack);
        assertThat(feedBackUsuarioParaContador.getUsuarioContador()).isEqualTo(usuarioContadorBack);

        feedBackUsuarioParaContador.usuarioContador(null);
        assertThat(feedBackUsuarioParaContador.getUsuarioContador()).isNull();
    }

    @Test
    void criterioAvaliacaoAtorTest() {
        FeedBackUsuarioParaContador feedBackUsuarioParaContador = getFeedBackUsuarioParaContadorRandomSampleGenerator();
        CriterioAvaliacaoAtor criterioAvaliacaoAtorBack = getCriterioAvaliacaoAtorRandomSampleGenerator();

        feedBackUsuarioParaContador.setCriterioAvaliacaoAtor(criterioAvaliacaoAtorBack);
        assertThat(feedBackUsuarioParaContador.getCriterioAvaliacaoAtor()).isEqualTo(criterioAvaliacaoAtorBack);

        feedBackUsuarioParaContador.criterioAvaliacaoAtor(null);
        assertThat(feedBackUsuarioParaContador.getCriterioAvaliacaoAtor()).isNull();
    }

    @Test
    void ordemServicoTest() {
        FeedBackUsuarioParaContador feedBackUsuarioParaContador = getFeedBackUsuarioParaContadorRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        feedBackUsuarioParaContador.setOrdemServico(ordemServicoBack);
        assertThat(feedBackUsuarioParaContador.getOrdemServico()).isEqualTo(ordemServicoBack);

        feedBackUsuarioParaContador.ordemServico(null);
        assertThat(feedBackUsuarioParaContador.getOrdemServico()).isNull();
    }
}
