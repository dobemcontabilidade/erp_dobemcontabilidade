package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgendaContadorConfigTestSamples.*;
import static com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgendaTarefaOrdemServicoExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaTarefaOrdemServicoExecucao.class);
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao1 = getAgendaTarefaOrdemServicoExecucaoSample1();
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao2 = new AgendaTarefaOrdemServicoExecucao();
        assertThat(agendaTarefaOrdemServicoExecucao1).isNotEqualTo(agendaTarefaOrdemServicoExecucao2);

        agendaTarefaOrdemServicoExecucao2.setId(agendaTarefaOrdemServicoExecucao1.getId());
        assertThat(agendaTarefaOrdemServicoExecucao1).isEqualTo(agendaTarefaOrdemServicoExecucao2);

        agendaTarefaOrdemServicoExecucao2 = getAgendaTarefaOrdemServicoExecucaoSample2();
        assertThat(agendaTarefaOrdemServicoExecucao1).isNotEqualTo(agendaTarefaOrdemServicoExecucao2);
    }

    @Test
    void agendaContadorConfigTest() {
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao = getAgendaTarefaOrdemServicoExecucaoRandomSampleGenerator();
        AgendaContadorConfig agendaContadorConfigBack = getAgendaContadorConfigRandomSampleGenerator();

        agendaTarefaOrdemServicoExecucao.addAgendaContadorConfig(agendaContadorConfigBack);
        assertThat(agendaTarefaOrdemServicoExecucao.getAgendaContadorConfigs()).containsOnly(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaOrdemServicoExecucao()).isEqualTo(agendaTarefaOrdemServicoExecucao);

        agendaTarefaOrdemServicoExecucao.removeAgendaContadorConfig(agendaContadorConfigBack);
        assertThat(agendaTarefaOrdemServicoExecucao.getAgendaContadorConfigs()).doesNotContain(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaOrdemServicoExecucao()).isNull();

        agendaTarefaOrdemServicoExecucao.agendaContadorConfigs(new HashSet<>(Set.of(agendaContadorConfigBack)));
        assertThat(agendaTarefaOrdemServicoExecucao.getAgendaContadorConfigs()).containsOnly(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaOrdemServicoExecucao()).isEqualTo(agendaTarefaOrdemServicoExecucao);

        agendaTarefaOrdemServicoExecucao.setAgendaContadorConfigs(new HashSet<>());
        assertThat(agendaTarefaOrdemServicoExecucao.getAgendaContadorConfigs()).doesNotContain(agendaContadorConfigBack);
        assertThat(agendaContadorConfigBack.getAgendaTarefaOrdemServicoExecucao()).isNull();
    }

    @Test
    void tarefaOrdemServicoExecucaoTest() {
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao = getAgendaTarefaOrdemServicoExecucaoRandomSampleGenerator();
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucaoBack = getTarefaOrdemServicoExecucaoRandomSampleGenerator();

        agendaTarefaOrdemServicoExecucao.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucaoBack);
        assertThat(agendaTarefaOrdemServicoExecucao.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucaoBack);

        agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao(null);
        assertThat(agendaTarefaOrdemServicoExecucao.getTarefaOrdemServicoExecucao()).isNull();
    }
}
