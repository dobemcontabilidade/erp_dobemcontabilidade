package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.CriterioAvaliacaoAtorTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackContadorParaUsuarioTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedBackContadorParaUsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBackContadorParaUsuario.class);
        FeedBackContadorParaUsuario feedBackContadorParaUsuario1 = getFeedBackContadorParaUsuarioSample1();
        FeedBackContadorParaUsuario feedBackContadorParaUsuario2 = new FeedBackContadorParaUsuario();
        assertThat(feedBackContadorParaUsuario1).isNotEqualTo(feedBackContadorParaUsuario2);

        feedBackContadorParaUsuario2.setId(feedBackContadorParaUsuario1.getId());
        assertThat(feedBackContadorParaUsuario1).isEqualTo(feedBackContadorParaUsuario2);

        feedBackContadorParaUsuario2 = getFeedBackContadorParaUsuarioSample2();
        assertThat(feedBackContadorParaUsuario1).isNotEqualTo(feedBackContadorParaUsuario2);
    }

    @Test
    void criterioAvaliacaoAtorTest() {
        FeedBackContadorParaUsuario feedBackContadorParaUsuario = getFeedBackContadorParaUsuarioRandomSampleGenerator();
        CriterioAvaliacaoAtor criterioAvaliacaoAtorBack = getCriterioAvaliacaoAtorRandomSampleGenerator();

        feedBackContadorParaUsuario.setCriterioAvaliacaoAtor(criterioAvaliacaoAtorBack);
        assertThat(feedBackContadorParaUsuario.getCriterioAvaliacaoAtor()).isEqualTo(criterioAvaliacaoAtorBack);

        feedBackContadorParaUsuario.criterioAvaliacaoAtor(null);
        assertThat(feedBackContadorParaUsuario.getCriterioAvaliacaoAtor()).isNull();
    }

    @Test
    void usuarioEmpresaTest() {
        FeedBackContadorParaUsuario feedBackContadorParaUsuario = getFeedBackContadorParaUsuarioRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        feedBackContadorParaUsuario.setUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(feedBackContadorParaUsuario.getUsuarioEmpresa()).isEqualTo(usuarioEmpresaBack);

        feedBackContadorParaUsuario.usuarioEmpresa(null);
        assertThat(feedBackContadorParaUsuario.getUsuarioEmpresa()).isNull();
    }

    @Test
    void contadorTest() {
        FeedBackContadorParaUsuario feedBackContadorParaUsuario = getFeedBackContadorParaUsuarioRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        feedBackContadorParaUsuario.setContador(contadorBack);
        assertThat(feedBackContadorParaUsuario.getContador()).isEqualTo(contadorBack);

        feedBackContadorParaUsuario.contador(null);
        assertThat(feedBackContadorParaUsuario.getContador()).isNull();
    }

    @Test
    void ordemServicoTest() {
        FeedBackContadorParaUsuario feedBackContadorParaUsuario = getFeedBackContadorParaUsuarioRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        feedBackContadorParaUsuario.setOrdemServico(ordemServicoBack);
        assertThat(feedBackContadorParaUsuario.getOrdemServico()).isEqualTo(ordemServicoBack);

        feedBackContadorParaUsuario.ordemServico(null);
        assertThat(feedBackContadorParaUsuario.getOrdemServico()).isNull();
    }
}
