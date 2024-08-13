package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorResponsavelOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContadorResponsavelOrdemServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContadorResponsavelOrdemServico.class);
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico1 = getContadorResponsavelOrdemServicoSample1();
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico2 = new ContadorResponsavelOrdemServico();
        assertThat(contadorResponsavelOrdemServico1).isNotEqualTo(contadorResponsavelOrdemServico2);

        contadorResponsavelOrdemServico2.setId(contadorResponsavelOrdemServico1.getId());
        assertThat(contadorResponsavelOrdemServico1).isEqualTo(contadorResponsavelOrdemServico2);

        contadorResponsavelOrdemServico2 = getContadorResponsavelOrdemServicoSample2();
        assertThat(contadorResponsavelOrdemServico1).isNotEqualTo(contadorResponsavelOrdemServico2);
    }

    @Test
    void tarefaOrdemServicoExecucaoTest() {
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico = getContadorResponsavelOrdemServicoRandomSampleGenerator();
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucaoBack = getTarefaOrdemServicoExecucaoRandomSampleGenerator();

        contadorResponsavelOrdemServico.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucaoBack);
        assertThat(contadorResponsavelOrdemServico.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucaoBack);

        contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao(null);
        assertThat(contadorResponsavelOrdemServico.getTarefaOrdemServicoExecucao()).isNull();
    }

    @Test
    void contadorTest() {
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico = getContadorResponsavelOrdemServicoRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        contadorResponsavelOrdemServico.setContador(contadorBack);
        assertThat(contadorResponsavelOrdemServico.getContador()).isEqualTo(contadorBack);

        contadorResponsavelOrdemServico.contador(null);
        assertThat(contadorResponsavelOrdemServico.getContador()).isNull();
    }
}
