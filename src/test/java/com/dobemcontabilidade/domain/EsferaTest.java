package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EsferaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EsferaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Esfera.class);
        Esfera esfera1 = getEsferaSample1();
        Esfera esfera2 = new Esfera();
        assertThat(esfera1).isNotEqualTo(esfera2);

        esfera2.setId(esfera1.getId());
        assertThat(esfera1).isEqualTo(esfera2);

        esfera2 = getEsferaSample2();
        assertThat(esfera1).isNotEqualTo(esfera2);
    }

    @Test
    void tarefaTest() {
        Esfera esfera = getEsferaRandomSampleGenerator();
        Tarefa tarefaBack = getTarefaRandomSampleGenerator();

        esfera.addTarefa(tarefaBack);
        assertThat(esfera.getTarefas()).containsOnly(tarefaBack);
        assertThat(tarefaBack.getEsfera()).isEqualTo(esfera);

        esfera.removeTarefa(tarefaBack);
        assertThat(esfera.getTarefas()).doesNotContain(tarefaBack);
        assertThat(tarefaBack.getEsfera()).isNull();

        esfera.tarefas(new HashSet<>(Set.of(tarefaBack)));
        assertThat(esfera.getTarefas()).containsOnly(tarefaBack);
        assertThat(tarefaBack.getEsfera()).isEqualTo(esfera);

        esfera.setTarefas(new HashSet<>());
        assertThat(esfera.getTarefas()).doesNotContain(tarefaBack);
        assertThat(tarefaBack.getEsfera()).isNull();
    }
}
