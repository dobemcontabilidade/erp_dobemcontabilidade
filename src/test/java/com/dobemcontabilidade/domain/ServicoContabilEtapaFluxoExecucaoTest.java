package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EtapaFluxoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicoContabilEtapaFluxoExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoContabilEtapaFluxoExecucao.class);
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao1 = getServicoContabilEtapaFluxoExecucaoSample1();
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao2 = new ServicoContabilEtapaFluxoExecucao();
        assertThat(servicoContabilEtapaFluxoExecucao1).isNotEqualTo(servicoContabilEtapaFluxoExecucao2);

        servicoContabilEtapaFluxoExecucao2.setId(servicoContabilEtapaFluxoExecucao1.getId());
        assertThat(servicoContabilEtapaFluxoExecucao1).isEqualTo(servicoContabilEtapaFluxoExecucao2);

        servicoContabilEtapaFluxoExecucao2 = getServicoContabilEtapaFluxoExecucaoSample2();
        assertThat(servicoContabilEtapaFluxoExecucao1).isNotEqualTo(servicoContabilEtapaFluxoExecucao2);
    }

    @Test
    void servicoContabilTest() {
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao = getServicoContabilEtapaFluxoExecucaoRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        servicoContabilEtapaFluxoExecucao.setServicoContabil(servicoContabilBack);
        assertThat(servicoContabilEtapaFluxoExecucao.getServicoContabil()).isEqualTo(servicoContabilBack);

        servicoContabilEtapaFluxoExecucao.servicoContabil(null);
        assertThat(servicoContabilEtapaFluxoExecucao.getServicoContabil()).isNull();
    }

    @Test
    void etapaFluxoExecucaoTest() {
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao = getServicoContabilEtapaFluxoExecucaoRandomSampleGenerator();
        EtapaFluxoExecucao etapaFluxoExecucaoBack = getEtapaFluxoExecucaoRandomSampleGenerator();

        servicoContabilEtapaFluxoExecucao.setEtapaFluxoExecucao(etapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucao.getEtapaFluxoExecucao()).isEqualTo(etapaFluxoExecucaoBack);

        servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao(null);
        assertThat(servicoContabilEtapaFluxoExecucao.getEtapaFluxoExecucao()).isNull();
    }
}
