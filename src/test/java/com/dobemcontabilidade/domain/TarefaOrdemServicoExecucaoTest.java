package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorResponsavelOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.SubTarefaOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TarefaOrdemServicoExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaOrdemServicoExecucao.class);
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao1 = getTarefaOrdemServicoExecucaoSample1();
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao2 = new TarefaOrdemServicoExecucao();
        assertThat(tarefaOrdemServicoExecucao1).isNotEqualTo(tarefaOrdemServicoExecucao2);

        tarefaOrdemServicoExecucao2.setId(tarefaOrdemServicoExecucao1.getId());
        assertThat(tarefaOrdemServicoExecucao1).isEqualTo(tarefaOrdemServicoExecucao2);

        tarefaOrdemServicoExecucao2 = getTarefaOrdemServicoExecucaoSample2();
        assertThat(tarefaOrdemServicoExecucao1).isNotEqualTo(tarefaOrdemServicoExecucao2);
    }

    @Test
    void agendaTarefaOrdemServicoExecucaoTest() {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = getTarefaOrdemServicoExecucaoRandomSampleGenerator();
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucaoBack = getAgendaTarefaOrdemServicoExecucaoRandomSampleGenerator();

        tarefaOrdemServicoExecucao.addAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucao.getAgendaTarefaOrdemServicoExecucaos()).containsOnly(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(agendaTarefaOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.removeAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucao.getAgendaTarefaOrdemServicoExecucaos()).doesNotContain(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(agendaTarefaOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isNull();

        tarefaOrdemServicoExecucao.agendaTarefaOrdemServicoExecucaos(new HashSet<>(Set.of(agendaTarefaOrdemServicoExecucaoBack)));
        assertThat(tarefaOrdemServicoExecucao.getAgendaTarefaOrdemServicoExecucaos()).containsOnly(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(agendaTarefaOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.setAgendaTarefaOrdemServicoExecucaos(new HashSet<>());
        assertThat(tarefaOrdemServicoExecucao.getAgendaTarefaOrdemServicoExecucaos()).doesNotContain(agendaTarefaOrdemServicoExecucaoBack);
        assertThat(agendaTarefaOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isNull();
    }

    @Test
    void anexoOrdemServicoExecucaoTest() {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = getTarefaOrdemServicoExecucaoRandomSampleGenerator();
        AnexoOrdemServicoExecucao anexoOrdemServicoExecucaoBack = getAnexoOrdemServicoExecucaoRandomSampleGenerator();

        tarefaOrdemServicoExecucao.addAnexoOrdemServicoExecucao(anexoOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucao.getAnexoOrdemServicoExecucaos()).containsOnly(anexoOrdemServicoExecucaoBack);
        assertThat(anexoOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.removeAnexoOrdemServicoExecucao(anexoOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucao.getAnexoOrdemServicoExecucaos()).doesNotContain(anexoOrdemServicoExecucaoBack);
        assertThat(anexoOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isNull();

        tarefaOrdemServicoExecucao.anexoOrdemServicoExecucaos(new HashSet<>(Set.of(anexoOrdemServicoExecucaoBack)));
        assertThat(tarefaOrdemServicoExecucao.getAnexoOrdemServicoExecucaos()).containsOnly(anexoOrdemServicoExecucaoBack);
        assertThat(anexoOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.setAnexoOrdemServicoExecucaos(new HashSet<>());
        assertThat(tarefaOrdemServicoExecucao.getAnexoOrdemServicoExecucaos()).doesNotContain(anexoOrdemServicoExecucaoBack);
        assertThat(anexoOrdemServicoExecucaoBack.getTarefaOrdemServicoExecucao()).isNull();
    }

    @Test
    void subTarefaOrdemServicoTest() {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = getTarefaOrdemServicoExecucaoRandomSampleGenerator();
        SubTarefaOrdemServico subTarefaOrdemServicoBack = getSubTarefaOrdemServicoRandomSampleGenerator();

        tarefaOrdemServicoExecucao.addSubTarefaOrdemServico(subTarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoExecucao.getSubTarefaOrdemServicos()).containsOnly(subTarefaOrdemServicoBack);
        assertThat(subTarefaOrdemServicoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.removeSubTarefaOrdemServico(subTarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoExecucao.getSubTarefaOrdemServicos()).doesNotContain(subTarefaOrdemServicoBack);
        assertThat(subTarefaOrdemServicoBack.getTarefaOrdemServicoExecucao()).isNull();

        tarefaOrdemServicoExecucao.subTarefaOrdemServicos(new HashSet<>(Set.of(subTarefaOrdemServicoBack)));
        assertThat(tarefaOrdemServicoExecucao.getSubTarefaOrdemServicos()).containsOnly(subTarefaOrdemServicoBack);
        assertThat(subTarefaOrdemServicoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.setSubTarefaOrdemServicos(new HashSet<>());
        assertThat(tarefaOrdemServicoExecucao.getSubTarefaOrdemServicos()).doesNotContain(subTarefaOrdemServicoBack);
        assertThat(subTarefaOrdemServicoBack.getTarefaOrdemServicoExecucao()).isNull();
    }

    @Test
    void contadorResponsavelOrdemServicoTest() {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = getTarefaOrdemServicoExecucaoRandomSampleGenerator();
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServicoBack = getContadorResponsavelOrdemServicoRandomSampleGenerator();

        tarefaOrdemServicoExecucao.addContadorResponsavelOrdemServico(contadorResponsavelOrdemServicoBack);
        assertThat(tarefaOrdemServicoExecucao.getContadorResponsavelOrdemServicos()).containsOnly(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.removeContadorResponsavelOrdemServico(contadorResponsavelOrdemServicoBack);
        assertThat(tarefaOrdemServicoExecucao.getContadorResponsavelOrdemServicos()).doesNotContain(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getTarefaOrdemServicoExecucao()).isNull();

        tarefaOrdemServicoExecucao.contadorResponsavelOrdemServicos(new HashSet<>(Set.of(contadorResponsavelOrdemServicoBack)));
        assertThat(tarefaOrdemServicoExecucao.getContadorResponsavelOrdemServicos()).containsOnly(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getTarefaOrdemServicoExecucao()).isEqualTo(tarefaOrdemServicoExecucao);

        tarefaOrdemServicoExecucao.setContadorResponsavelOrdemServicos(new HashSet<>());
        assertThat(tarefaOrdemServicoExecucao.getContadorResponsavelOrdemServicos()).doesNotContain(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getTarefaOrdemServicoExecucao()).isNull();
    }

    @Test
    void tarefaOrdemServicoTest() {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = getTarefaOrdemServicoExecucaoRandomSampleGenerator();
        TarefaOrdemServico tarefaOrdemServicoBack = getTarefaOrdemServicoRandomSampleGenerator();

        tarefaOrdemServicoExecucao.setTarefaOrdemServico(tarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoExecucao.getTarefaOrdemServico()).isEqualTo(tarefaOrdemServicoBack);

        tarefaOrdemServicoExecucao.tarefaOrdemServico(null);
        assertThat(tarefaOrdemServicoExecucao.getTarefaOrdemServico()).isNull();
    }
}
