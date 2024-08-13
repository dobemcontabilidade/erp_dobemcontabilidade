package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FrequenciaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FrequenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frequencia.class);
        Frequencia frequencia1 = getFrequenciaSample1();
        Frequencia frequencia2 = new Frequencia();
        assertThat(frequencia1).isNotEqualTo(frequencia2);

        frequencia2.setId(frequencia1.getId());
        assertThat(frequencia1).isEqualTo(frequencia2);

        frequencia2 = getFrequenciaSample2();
        assertThat(frequencia1).isNotEqualTo(frequencia2);
    }

    @Test
    void tarefaTest() {
        Frequencia frequencia = getFrequenciaRandomSampleGenerator();
        Tarefa tarefaBack = getTarefaRandomSampleGenerator();

        frequencia.addTarefa(tarefaBack);
        assertThat(frequencia.getTarefas()).containsOnly(tarefaBack);
        assertThat(tarefaBack.getFrequencia()).isEqualTo(frequencia);

        frequencia.removeTarefa(tarefaBack);
        assertThat(frequencia.getTarefas()).doesNotContain(tarefaBack);
        assertThat(tarefaBack.getFrequencia()).isNull();

        frequencia.tarefas(new HashSet<>(Set.of(tarefaBack)));
        assertThat(frequencia.getTarefas()).containsOnly(tarefaBack);
        assertThat(tarefaBack.getFrequencia()).isEqualTo(frequencia);

        frequencia.setTarefas(new HashSet<>());
        assertThat(frequencia.getTarefas()).doesNotContain(tarefaBack);
        assertThat(tarefaBack.getFrequencia()).isNull();
    }
}
