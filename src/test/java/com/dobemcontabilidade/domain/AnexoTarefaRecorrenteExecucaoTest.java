package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoTarefaRecorrenteExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoTarefaRecorrenteExecucao.class);
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao1 = getAnexoTarefaRecorrenteExecucaoSample1();
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao2 = new AnexoTarefaRecorrenteExecucao();
        assertThat(anexoTarefaRecorrenteExecucao1).isNotEqualTo(anexoTarefaRecorrenteExecucao2);

        anexoTarefaRecorrenteExecucao2.setId(anexoTarefaRecorrenteExecucao1.getId());
        assertThat(anexoTarefaRecorrenteExecucao1).isEqualTo(anexoTarefaRecorrenteExecucao2);

        anexoTarefaRecorrenteExecucao2 = getAnexoTarefaRecorrenteExecucaoSample2();
        assertThat(anexoTarefaRecorrenteExecucao1).isNotEqualTo(anexoTarefaRecorrenteExecucao2);
    }

    @Test
    void tarefaRecorrenteExecucaoTest() {
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao = getAnexoTarefaRecorrenteExecucaoRandomSampleGenerator();
        TarefaRecorrenteExecucao tarefaRecorrenteExecucaoBack = getTarefaRecorrenteExecucaoRandomSampleGenerator();

        anexoTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucaoBack);
        assertThat(anexoTarefaRecorrenteExecucao.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucaoBack);

        anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao(null);
        assertThat(anexoTarefaRecorrenteExecucao.getTarefaRecorrenteExecucao()).isNull();
    }
}
