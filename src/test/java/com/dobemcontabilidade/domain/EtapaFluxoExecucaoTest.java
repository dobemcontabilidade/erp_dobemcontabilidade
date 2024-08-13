package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EtapaFluxoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EtapaFluxoExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaFluxoExecucao.class);
        EtapaFluxoExecucao etapaFluxoExecucao1 = getEtapaFluxoExecucaoSample1();
        EtapaFluxoExecucao etapaFluxoExecucao2 = new EtapaFluxoExecucao();
        assertThat(etapaFluxoExecucao1).isNotEqualTo(etapaFluxoExecucao2);

        etapaFluxoExecucao2.setId(etapaFluxoExecucao1.getId());
        assertThat(etapaFluxoExecucao1).isEqualTo(etapaFluxoExecucao2);

        etapaFluxoExecucao2 = getEtapaFluxoExecucaoSample2();
        assertThat(etapaFluxoExecucao1).isNotEqualTo(etapaFluxoExecucao2);
    }

    @Test
    void servicoContabilEtapaFluxoExecucaoTest() {
        EtapaFluxoExecucao etapaFluxoExecucao = getEtapaFluxoExecucaoRandomSampleGenerator();
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucaoBack =
            getServicoContabilEtapaFluxoExecucaoRandomSampleGenerator();

        etapaFluxoExecucao.addServicoContabilEtapaFluxoExecucao(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(etapaFluxoExecucao.getServicoContabilEtapaFluxoExecucaos()).containsOnly(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getEtapaFluxoExecucao()).isEqualTo(etapaFluxoExecucao);

        etapaFluxoExecucao.removeServicoContabilEtapaFluxoExecucao(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(etapaFluxoExecucao.getServicoContabilEtapaFluxoExecucaos()).doesNotContain(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getEtapaFluxoExecucao()).isNull();

        etapaFluxoExecucao.servicoContabilEtapaFluxoExecucaos(new HashSet<>(Set.of(servicoContabilEtapaFluxoExecucaoBack)));
        assertThat(etapaFluxoExecucao.getServicoContabilEtapaFluxoExecucaos()).containsOnly(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getEtapaFluxoExecucao()).isEqualTo(etapaFluxoExecucao);

        etapaFluxoExecucao.setServicoContabilEtapaFluxoExecucaos(new HashSet<>());
        assertThat(etapaFluxoExecucao.getServicoContabilEtapaFluxoExecucaos()).doesNotContain(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getEtapaFluxoExecucao()).isNull();
    }

    @Test
    void ordemServicoTest() {
        EtapaFluxoExecucao etapaFluxoExecucao = getEtapaFluxoExecucaoRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        etapaFluxoExecucao.setOrdemServico(ordemServicoBack);
        assertThat(etapaFluxoExecucao.getOrdemServico()).isEqualTo(ordemServicoBack);

        etapaFluxoExecucao.ordemServico(null);
        assertThat(etapaFluxoExecucao.getOrdemServico()).isNull();
    }
}
