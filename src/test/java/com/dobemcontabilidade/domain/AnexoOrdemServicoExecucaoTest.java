package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoOrdemServicoExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoOrdemServicoExecucao.class);
        AnexoOrdemServicoExecucao anexoOrdemServicoExecucao1 = getAnexoOrdemServicoExecucaoSample1();
        AnexoOrdemServicoExecucao anexoOrdemServicoExecucao2 = new AnexoOrdemServicoExecucao();
        assertThat(anexoOrdemServicoExecucao1).isNotEqualTo(anexoOrdemServicoExecucao2);

        anexoOrdemServicoExecucao2.setId(anexoOrdemServicoExecucao1.getId());
        assertThat(anexoOrdemServicoExecucao1).isEqualTo(anexoOrdemServicoExecucao2);

        anexoOrdemServicoExecucao2 = getAnexoOrdemServicoExecucaoSample2();
        assertThat(anexoOrdemServicoExecucao1).isNotEqualTo(anexoOrdemServicoExecucao2);
    }

    @Test
    void tarefaOrdemServicoExecucaoTest() {
        AnexoOrdemServicoExecucao anexoOrdemServicoExecucao = getAnexoOrdemServicoExecucaoRandomSampleGenerator();
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucaoBack = getTarefaOrdemServicoExecucaoRandomSampleGenerator();

        anexoOrdemServicoExecucao.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucaoBack);
        assertThat(anexoOrdemServicoExecucao.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucaoBack);

        anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao(null);
        assertThat(anexoOrdemServicoExecucao.getTarefaOrdemServicoExecucao()).isNull();
    }
}
