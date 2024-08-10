package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CompetenciaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompetenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competencia.class);
        Competencia competencia1 = getCompetenciaSample1();
        Competencia competencia2 = new Competencia();
        assertThat(competencia1).isNotEqualTo(competencia2);

        competencia2.setId(competencia1.getId());
        assertThat(competencia1).isEqualTo(competencia2);

        competencia2 = getCompetenciaSample2();
        assertThat(competencia1).isNotEqualTo(competencia2);
    }

    @Test
    void tarefaTest() {
        Competencia competencia = getCompetenciaRandomSampleGenerator();
        Tarefa tarefaBack = getTarefaRandomSampleGenerator();

        competencia.addTarefa(tarefaBack);
        assertThat(competencia.getTarefas()).containsOnly(tarefaBack);
        assertThat(tarefaBack.getCompetencia()).isEqualTo(competencia);

        competencia.removeTarefa(tarefaBack);
        assertThat(competencia.getTarefas()).doesNotContain(tarefaBack);
        assertThat(tarefaBack.getCompetencia()).isNull();

        competencia.tarefas(new HashSet<>(Set.of(tarefaBack)));
        assertThat(competencia.getTarefas()).containsOnly(tarefaBack);
        assertThat(tarefaBack.getCompetencia()).isEqualTo(competencia);

        competencia.setTarefas(new HashSet<>());
        assertThat(competencia.getTarefas()).doesNotContain(tarefaBack);
        assertThat(tarefaBack.getCompetencia()).isNull();
    }
}
