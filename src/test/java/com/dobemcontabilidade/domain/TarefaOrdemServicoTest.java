package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TarefaOrdemServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaOrdemServico.class);
        TarefaOrdemServico tarefaOrdemServico1 = getTarefaOrdemServicoSample1();
        TarefaOrdemServico tarefaOrdemServico2 = new TarefaOrdemServico();
        assertThat(tarefaOrdemServico1).isNotEqualTo(tarefaOrdemServico2);

        tarefaOrdemServico2.setId(tarefaOrdemServico1.getId());
        assertThat(tarefaOrdemServico1).isEqualTo(tarefaOrdemServico2);

        tarefaOrdemServico2 = getTarefaOrdemServicoSample2();
        assertThat(tarefaOrdemServico1).isNotEqualTo(tarefaOrdemServico2);
    }

    @Test
    void anexoRequeridoTarefaOrdemServicoTest() {
        TarefaOrdemServico tarefaOrdemServico = getTarefaOrdemServicoRandomSampleGenerator();
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServicoBack = getAnexoRequeridoTarefaOrdemServicoRandomSampleGenerator();

        tarefaOrdemServico.addAnexoRequeridoTarefaOrdemServico(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(tarefaOrdemServico.getAnexoRequeridoTarefaOrdemServicos()).containsOnly(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getTarefaOrdemServico()).isEqualTo(tarefaOrdemServico);

        tarefaOrdemServico.removeAnexoRequeridoTarefaOrdemServico(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(tarefaOrdemServico.getAnexoRequeridoTarefaOrdemServicos()).doesNotContain(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getTarefaOrdemServico()).isNull();

        tarefaOrdemServico.anexoRequeridoTarefaOrdemServicos(new HashSet<>(Set.of(anexoRequeridoTarefaOrdemServicoBack)));
        assertThat(tarefaOrdemServico.getAnexoRequeridoTarefaOrdemServicos()).containsOnly(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getTarefaOrdemServico()).isEqualTo(tarefaOrdemServico);

        tarefaOrdemServico.setAnexoRequeridoTarefaOrdemServicos(new HashSet<>());
        assertThat(tarefaOrdemServico.getAnexoRequeridoTarefaOrdemServicos()).doesNotContain(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getTarefaOrdemServico()).isNull();
    }

    @Test
    void tarefaOrdemServicoExecucaoTest() {
        TarefaOrdemServico tarefaOrdemServico = getTarefaOrdemServicoRandomSampleGenerator();
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucaoBack = getTarefaOrdemServicoExecucaoRandomSampleGenerator();

        tarefaOrdemServico.addTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServico.getTarefaOrdemServicoExecucaos()).containsOnly(tarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucaoBack.getTarefaOrdemServico()).isEqualTo(tarefaOrdemServico);

        tarefaOrdemServico.removeTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServico.getTarefaOrdemServicoExecucaos()).doesNotContain(tarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucaoBack.getTarefaOrdemServico()).isNull();

        tarefaOrdemServico.tarefaOrdemServicoExecucaos(new HashSet<>(Set.of(tarefaOrdemServicoExecucaoBack)));
        assertThat(tarefaOrdemServico.getTarefaOrdemServicoExecucaos()).containsOnly(tarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucaoBack.getTarefaOrdemServico()).isEqualTo(tarefaOrdemServico);

        tarefaOrdemServico.setTarefaOrdemServicoExecucaos(new HashSet<>());
        assertThat(tarefaOrdemServico.getTarefaOrdemServicoExecucaos()).doesNotContain(tarefaOrdemServicoExecucaoBack);
        assertThat(tarefaOrdemServicoExecucaoBack.getTarefaOrdemServico()).isNull();
    }

    @Test
    void servicoContabilOrdemServicoTest() {
        TarefaOrdemServico tarefaOrdemServico = getTarefaOrdemServicoRandomSampleGenerator();
        ServicoContabilOrdemServico servicoContabilOrdemServicoBack = getServicoContabilOrdemServicoRandomSampleGenerator();

        tarefaOrdemServico.setServicoContabilOrdemServico(servicoContabilOrdemServicoBack);
        assertThat(tarefaOrdemServico.getServicoContabilOrdemServico()).isEqualTo(servicoContabilOrdemServicoBack);

        tarefaOrdemServico.servicoContabilOrdemServico(null);
        assertThat(tarefaOrdemServico.getServicoContabilOrdemServico()).isNull();
    }
}
