package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.SubTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubTarefaRecorrenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubTarefaRecorrente.class);
        SubTarefaRecorrente subTarefaRecorrente1 = getSubTarefaRecorrenteSample1();
        SubTarefaRecorrente subTarefaRecorrente2 = new SubTarefaRecorrente();
        assertThat(subTarefaRecorrente1).isNotEqualTo(subTarefaRecorrente2);

        subTarefaRecorrente2.setId(subTarefaRecorrente1.getId());
        assertThat(subTarefaRecorrente1).isEqualTo(subTarefaRecorrente2);

        subTarefaRecorrente2 = getSubTarefaRecorrenteSample2();
        assertThat(subTarefaRecorrente1).isNotEqualTo(subTarefaRecorrente2);
    }

    @Test
    void tarefaRecorrenteExecucaoTest() {
        SubTarefaRecorrente subTarefaRecorrente = getSubTarefaRecorrenteRandomSampleGenerator();
        TarefaRecorrenteExecucao tarefaRecorrenteExecucaoBack = getTarefaRecorrenteExecucaoRandomSampleGenerator();

        subTarefaRecorrente.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucaoBack);
        assertThat(subTarefaRecorrente.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucaoBack);

        subTarefaRecorrente.tarefaRecorrenteExecucao(null);
        assertThat(subTarefaRecorrente.getTarefaRecorrenteExecucao()).isNull();
    }
}
