package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgendaContadorConfigTestSamples.*;
import static com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgendaTarefaRecorrenteExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaTarefaRecorrenteExecucao.class);
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao1 = getAgendaTarefaRecorrenteExecucaoSample1();
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao2 = new AgendaTarefaRecorrenteExecucao();
        assertThat(agendaTarefaRecorrenteExecucao1).isNotEqualTo(agendaTarefaRecorrenteExecucao2);

        agendaTarefaRecorrenteExecucao2.setId(agendaTarefaRecorrenteExecucao1.getId());
        assertThat(agendaTarefaRecorrenteExecucao1).isEqualTo(agendaTarefaRecorrenteExecucao2);

        agendaTarefaRecorrenteExecucao2 = getAgendaTarefaRecorrenteExecucaoSample2();
        assertThat(agendaTarefaRecorrenteExecucao1).isNotEqualTo(agendaTarefaRecorrenteExecucao2);
    }

    @Test
    void agendaContadorConfigTest() {
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao = getAgendaTarefaRecorrenteExecucaoRandomSampleGenerator();
        AgendaContadorConfig agendaContadorConfigBack = getAgendaContadorConfigRandomSampleGenerator();

        agendaTarefaRecorrenteExecucao.addAgendaContadorConfig(agendaContadorConfigBack);
        assertThat(agendaTarefaRecorrenteExecucao.getAgendaContadorConfigs()).containsOnly(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaRecorrenteExecucao()).isEqualTo(agendaTarefaRecorrenteExecucao);

        agendaTarefaRecorrenteExecucao.removeAgendaContadorConfig(agendaContadorConfigBack);
        assertThat(agendaTarefaRecorrenteExecucao.getAgendaContadorConfigs()).doesNotContain(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaRecorrenteExecucao()).isNull();

        agendaTarefaRecorrenteExecucao.agendaContadorConfigs(new HashSet<>(Set.of(agendaContadorConfigBack)));
        assertThat(agendaTarefaRecorrenteExecucao.getAgendaContadorConfigs()).containsOnly(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaRecorrenteExecucao()).isEqualTo(agendaTarefaRecorrenteExecucao);

        agendaTarefaRecorrenteExecucao.setAgendaContadorConfigs(new HashSet<>());
        assertThat(agendaTarefaRecorrenteExecucao.getAgendaContadorConfigs()).doesNotContain(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void tarefaRecorrenteExecucaoTest() {
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao = getAgendaTarefaRecorrenteExecucaoRandomSampleGenerator();
        TarefaRecorrenteExecucao tarefaRecorrenteExecucaoBack = getTarefaRecorrenteExecucaoRandomSampleGenerator();

        agendaTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucaoBack);
        assertThat(agendaTarefaRecorrenteExecucao.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucaoBack);

        agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao(null);
        assertThat(agendaTarefaRecorrenteExecucao.getTarefaRecorrenteExecucao()).isNull();
    }
}
