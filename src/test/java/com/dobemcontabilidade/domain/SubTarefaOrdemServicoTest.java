package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.SubTarefaOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubTarefaOrdemServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubTarefaOrdemServico.class);
        SubTarefaOrdemServico subTarefaOrdemServico1 = getSubTarefaOrdemServicoSample1();
        SubTarefaOrdemServico subTarefaOrdemServico2 = new SubTarefaOrdemServico();
        assertThat(subTarefaOrdemServico1).isNotEqualTo(subTarefaOrdemServico2);

        subTarefaOrdemServico2.setId(subTarefaOrdemServico1.getId());
        assertThat(subTarefaOrdemServico1).isEqualTo(subTarefaOrdemServico2);

        subTarefaOrdemServico2 = getSubTarefaOrdemServicoSample2();
        assertThat(subTarefaOrdemServico1).isNotEqualTo(subTarefaOrdemServico2);
    }

    @Test
    void tarefaOrdemServicoExecucaoTest() {
        SubTarefaOrdemServico subTarefaOrdemServico = getSubTarefaOrdemServicoRandomSampleGenerator();
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucaoBack = getTarefaOrdemServicoExecucaoRandomSampleGenerator();

        subTarefaOrdemServico.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucaoBack);
        assertThat(subTarefaOrdemServico.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucaoBack);

        subTarefaOrdemServico.tarefaOrdemServicoExecucao(null);
        assertThat(subTarefaOrdemServico.getTarefaOrdemServicoExecucao()).isNull();
    }
}
