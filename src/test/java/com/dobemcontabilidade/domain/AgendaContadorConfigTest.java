package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgendaContadorConfigTestSamples.*;
import static com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendaContadorConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaContadorConfig.class);
        AgendaContadorConfig agendaContadorConfig1 = getAgendaContadorConfigSample1();
        AgendaContadorConfig agendaContadorConfig2 = new AgendaContadorConfig();
        assertThat(agendaContadorConfig1).isNotEqualTo(agendaContadorConfig2);

        agendaContadorConfig2.setId(agendaContadorConfig1.getId());
        assertThat(agendaContadorConfig1).isEqualTo(agendaContadorConfig2);

        agendaContadorConfig2 = getAgendaContadorConfigSample2();
        assertThat(agendaContadorConfig1).isNotEqualTo(agendaContadorConfig2);
    }

    @Test
    void agendaTarefaRecorrenteExecucaoTest() {
        AgendaContadorConfig agendaContadorConfig = getAgendaContadorConfigRandomSampleGenerator();
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucaoBack = getAgendaTarefaRecorrenteExecucaoRandomSampleGenerator();

        agendaContadorConfig.setAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucaoBack);
        assertThat(agendaContadorConfig.getAgendaTarefaRecorrenteExecucao()).isEqualTo(agendaTarefaRecorrenteExecucaoBack);

        agendaContadorConfig.agendaTarefaRecorrenteExecucao(null);
        assertThat(agendaContadorConfig.getAgendaTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void agendaTarefaOrdemServicoExecucaoTest() {
        AgendaContadorConfig agendaContadorConfig = getAgendaContadorConfigRandomSampleGenerator();
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucaoBack = getAgendaTarefaOrdemServicoExecucaoRandomSampleGenerator();

        agendaContadorConfig.setAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(agendaContadorConfig.getAgendaTarefaOrdemServicoExecucao()).isEqualTo(agendaTarefaOrdemServicoExecucaoBack);

        agendaContadorConfig.agendaTarefaOrdemServicoExecucao(null);
        assertThat(agendaContadorConfig.getAgendaTarefaOrdemServicoExecucao()).isNull();
    }
}
