package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CompetenciaTestSamples.*;
import static com.dobemcontabilidade.domain.EsferaTestSamples.*;
import static com.dobemcontabilidade.domain.FrequenciaTestSamples.*;
import static com.dobemcontabilidade.domain.SubtarefaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TarefaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarefa.class);
        Tarefa tarefa1 = getTarefaSample1();
        Tarefa tarefa2 = new Tarefa();
        assertThat(tarefa1).isNotEqualTo(tarefa2);

        tarefa2.setId(tarefa1.getId());
        assertThat(tarefa1).isEqualTo(tarefa2);

        tarefa2 = getTarefaSample2();
        assertThat(tarefa1).isNotEqualTo(tarefa2);
    }

    @Test
    void tarefaEmpresaTest() {
        Tarefa tarefa = getTarefaRandomSampleGenerator();
        TarefaEmpresa tarefaEmpresaBack = getTarefaEmpresaRandomSampleGenerator();

        tarefa.addTarefaEmpresa(tarefaEmpresaBack);
        assertThat(tarefa.getTarefaEmpresas()).containsOnly(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getTarefa()).isEqualTo(tarefa);

        tarefa.removeTarefaEmpresa(tarefaEmpresaBack);
        assertThat(tarefa.getTarefaEmpresas()).doesNotContain(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getTarefa()).isNull();

        tarefa.tarefaEmpresas(new HashSet<>(Set.of(tarefaEmpresaBack)));
        assertThat(tarefa.getTarefaEmpresas()).containsOnly(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getTarefa()).isEqualTo(tarefa);

        tarefa.setTarefaEmpresas(new HashSet<>());
        assertThat(tarefa.getTarefaEmpresas()).doesNotContain(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getTarefa()).isNull();
    }

    @Test
    void subtarefaTest() {
        Tarefa tarefa = getTarefaRandomSampleGenerator();
        Subtarefa subtarefaBack = getSubtarefaRandomSampleGenerator();

        tarefa.addSubtarefa(subtarefaBack);
        assertThat(tarefa.getSubtarefas()).containsOnly(subtarefaBack);
        assertThat(subtarefaBack.getTarefa()).isEqualTo(tarefa);

        tarefa.removeSubtarefa(subtarefaBack);
        assertThat(tarefa.getSubtarefas()).doesNotContain(subtarefaBack);
        assertThat(subtarefaBack.getTarefa()).isNull();

        tarefa.subtarefas(new HashSet<>(Set.of(subtarefaBack)));
        assertThat(tarefa.getSubtarefas()).containsOnly(subtarefaBack);
        assertThat(subtarefaBack.getTarefa()).isEqualTo(tarefa);

        tarefa.setSubtarefas(new HashSet<>());
        assertThat(tarefa.getSubtarefas()).doesNotContain(subtarefaBack);
        assertThat(subtarefaBack.getTarefa()).isNull();
    }

    @Test
    void esferaTest() {
        Tarefa tarefa = getTarefaRandomSampleGenerator();
        Esfera esferaBack = getEsferaRandomSampleGenerator();

        tarefa.setEsfera(esferaBack);
        assertThat(tarefa.getEsfera()).isEqualTo(esferaBack);

        tarefa.esfera(null);
        assertThat(tarefa.getEsfera()).isNull();
    }

    @Test
    void frequenciaTest() {
        Tarefa tarefa = getTarefaRandomSampleGenerator();
        Frequencia frequenciaBack = getFrequenciaRandomSampleGenerator();

        tarefa.setFrequencia(frequenciaBack);
        assertThat(tarefa.getFrequencia()).isEqualTo(frequenciaBack);

        tarefa.frequencia(null);
        assertThat(tarefa.getFrequencia()).isNull();
    }

    @Test
    void competenciaTest() {
        Tarefa tarefa = getTarefaRandomSampleGenerator();
        Competencia competenciaBack = getCompetenciaRandomSampleGenerator();

        tarefa.setCompetencia(competenciaBack);
        assertThat(tarefa.getCompetencia()).isEqualTo(competenciaBack);

        tarefa.competencia(null);
        assertThat(tarefa.getCompetencia()).isNull();
    }
}
