package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.SubtarefaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubtarefaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subtarefa.class);
        Subtarefa subtarefa1 = getSubtarefaSample1();
        Subtarefa subtarefa2 = new Subtarefa();
        assertThat(subtarefa1).isNotEqualTo(subtarefa2);

        subtarefa2.setId(subtarefa1.getId());
        assertThat(subtarefa1).isEqualTo(subtarefa2);

        subtarefa2 = getSubtarefaSample2();
        assertThat(subtarefa1).isNotEqualTo(subtarefa2);
    }

    @Test
    void tarefaTest() {
        Subtarefa subtarefa = getSubtarefaRandomSampleGenerator();
        Tarefa tarefaBack = getTarefaRandomSampleGenerator();

        subtarefa.setTarefa(tarefaBack);
        assertThat(subtarefa.getTarefa()).isEqualTo(tarefaBack);

        subtarefa.tarefa(null);
        assertThat(subtarefa.getTarefa()).isNull();
    }
}
