package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EtapaFluxoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackContadorParaUsuarioTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackUsuarioParaContadorTestSamples.*;
import static com.dobemcontabilidade.domain.FluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilOrdemServicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OrdemServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdemServico.class);
        OrdemServico ordemServico1 = getOrdemServicoSample1();
        OrdemServico ordemServico2 = new OrdemServico();
        assertThat(ordemServico1).isNotEqualTo(ordemServico2);

        ordemServico2.setId(ordemServico1.getId());
        assertThat(ordemServico1).isEqualTo(ordemServico2);

        ordemServico2 = getOrdemServicoSample2();
        assertThat(ordemServico1).isNotEqualTo(ordemServico2);
    }

    @Test
    void feedBackUsuarioParaContadorTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        FeedBackUsuarioParaContador feedBackUsuarioParaContadorBack = getFeedBackUsuarioParaContadorRandomSampleGenerator();

        ordemServico.addFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(ordemServico.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.removeFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(ordemServico.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getOrdemServico()).isNull();

        ordemServico.feedBackUsuarioParaContadors(new HashSet<>(Set.of(feedBackUsuarioParaContadorBack)));
        assertThat(ordemServico.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.setFeedBackUsuarioParaContadors(new HashSet<>());
        assertThat(ordemServico.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getOrdemServico()).isNull();
    }

    @Test
    void feedBackContadorParaUsuarioTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        FeedBackContadorParaUsuario feedBackContadorParaUsuarioBack = getFeedBackContadorParaUsuarioRandomSampleGenerator();

        ordemServico.addFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(ordemServico.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.removeFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(ordemServico.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getOrdemServico()).isNull();

        ordemServico.feedBackContadorParaUsuarios(new HashSet<>(Set.of(feedBackContadorParaUsuarioBack)));
        assertThat(ordemServico.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.setFeedBackContadorParaUsuarios(new HashSet<>());
        assertThat(ordemServico.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getOrdemServico()).isNull();
    }

    @Test
    void etapaFluxoExecucaoTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        EtapaFluxoExecucao etapaFluxoExecucaoBack = getEtapaFluxoExecucaoRandomSampleGenerator();

        ordemServico.addEtapaFluxoExecucao(etapaFluxoExecucaoBack);
        assertThat(ordemServico.getEtapaFluxoExecucaos()).containsOnly(etapaFluxoExecucaoBack);
        assertThat(etapaFluxoExecucaoBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.removeEtapaFluxoExecucao(etapaFluxoExecucaoBack);
        assertThat(ordemServico.getEtapaFluxoExecucaos()).doesNotContain(etapaFluxoExecucaoBack);
        assertThat(etapaFluxoExecucaoBack.getOrdemServico()).isNull();

        ordemServico.etapaFluxoExecucaos(new HashSet<>(Set.of(etapaFluxoExecucaoBack)));
        assertThat(ordemServico.getEtapaFluxoExecucaos()).containsOnly(etapaFluxoExecucaoBack);
        assertThat(etapaFluxoExecucaoBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.setEtapaFluxoExecucaos(new HashSet<>());
        assertThat(ordemServico.getEtapaFluxoExecucaos()).doesNotContain(etapaFluxoExecucaoBack);
        assertThat(etapaFluxoExecucaoBack.getOrdemServico()).isNull();
    }

    @Test
    void servicoContabilOrdemServicoTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        ServicoContabilOrdemServico servicoContabilOrdemServicoBack = getServicoContabilOrdemServicoRandomSampleGenerator();

        ordemServico.addServicoContabilOrdemServico(servicoContabilOrdemServicoBack);
        assertThat(ordemServico.getServicoContabilOrdemServicos()).containsOnly(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.removeServicoContabilOrdemServico(servicoContabilOrdemServicoBack);
        assertThat(ordemServico.getServicoContabilOrdemServicos()).doesNotContain(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getOrdemServico()).isNull();

        ordemServico.servicoContabilOrdemServicos(new HashSet<>(Set.of(servicoContabilOrdemServicoBack)));
        assertThat(ordemServico.getServicoContabilOrdemServicos()).containsOnly(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getOrdemServico()).isEqualTo(ordemServico);

        ordemServico.setServicoContabilOrdemServicos(new HashSet<>());
        assertThat(ordemServico.getServicoContabilOrdemServicos()).doesNotContain(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getOrdemServico()).isNull();
    }

    @Test
    void empresaTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        ordemServico.setEmpresa(empresaBack);
        assertThat(ordemServico.getEmpresa()).isEqualTo(empresaBack);

        ordemServico.empresa(null);
        assertThat(ordemServico.getEmpresa()).isNull();
    }

    @Test
    void contadorTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        ordemServico.setContador(contadorBack);
        assertThat(ordemServico.getContador()).isEqualTo(contadorBack);

        ordemServico.contador(null);
        assertThat(ordemServico.getContador()).isNull();
    }

    @Test
    void fluxoModeloTest() {
        OrdemServico ordemServico = getOrdemServicoRandomSampleGenerator();
        FluxoModelo fluxoModeloBack = getFluxoModeloRandomSampleGenerator();

        ordemServico.setFluxoModelo(fluxoModeloBack);
        assertThat(ordemServico.getFluxoModelo()).isEqualTo(fluxoModeloBack);

        ordemServico.fluxoModelo(null);
        assertThat(ordemServico.getFluxoModelo()).isNull();
    }
}
