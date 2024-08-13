package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServicoContabilOrdemServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoContabilOrdemServico.class);
        ServicoContabilOrdemServico servicoContabilOrdemServico1 = getServicoContabilOrdemServicoSample1();
        ServicoContabilOrdemServico servicoContabilOrdemServico2 = new ServicoContabilOrdemServico();
        assertThat(servicoContabilOrdemServico1).isNotEqualTo(servicoContabilOrdemServico2);

        servicoContabilOrdemServico2.setId(servicoContabilOrdemServico1.getId());
        assertThat(servicoContabilOrdemServico1).isEqualTo(servicoContabilOrdemServico2);

        servicoContabilOrdemServico2 = getServicoContabilOrdemServicoSample2();
        assertThat(servicoContabilOrdemServico1).isNotEqualTo(servicoContabilOrdemServico2);
    }

    @Test
    void tarefaOrdemServicoTest() {
        ServicoContabilOrdemServico servicoContabilOrdemServico = getServicoContabilOrdemServicoRandomSampleGenerator();
        TarefaOrdemServico tarefaOrdemServicoBack = getTarefaOrdemServicoRandomSampleGenerator();

        servicoContabilOrdemServico.addTarefaOrdemServico(tarefaOrdemServicoBack);
        assertThat(servicoContabilOrdemServico.getTarefaOrdemServicos()).containsOnly(tarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoBack.getServicoContabilOrdemServico()).isEqualTo(servicoContabilOrdemServico);

        servicoContabilOrdemServico.removeTarefaOrdemServico(tarefaOrdemServicoBack);
        assertThat(servicoContabilOrdemServico.getTarefaOrdemServicos()).doesNotContain(tarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoBack.getServicoContabilOrdemServico()).isNull();

        servicoContabilOrdemServico.tarefaOrdemServicos(new HashSet<>(Set.of(tarefaOrdemServicoBack)));
        assertThat(servicoContabilOrdemServico.getTarefaOrdemServicos()).containsOnly(tarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoBack.getServicoContabilOrdemServico()).isEqualTo(servicoContabilOrdemServico);

        servicoContabilOrdemServico.setTarefaOrdemServicos(new HashSet<>());
        assertThat(servicoContabilOrdemServico.getTarefaOrdemServicos()).doesNotContain(tarefaOrdemServicoBack);
        assertThat(tarefaOrdemServicoBack.getServicoContabilOrdemServico()).isNull();
    }

    @Test
    void servicoContabilTest() {
        ServicoContabilOrdemServico servicoContabilOrdemServico = getServicoContabilOrdemServicoRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        servicoContabilOrdemServico.setServicoContabil(servicoContabilBack);
        assertThat(servicoContabilOrdemServico.getServicoContabil()).isEqualTo(servicoContabilBack);

        servicoContabilOrdemServico.servicoContabil(null);
        assertThat(servicoContabilOrdemServico.getServicoContabil()).isNull();
    }

    @Test
    void ordemServicoTest() {
        ServicoContabilOrdemServico servicoContabilOrdemServico = getServicoContabilOrdemServicoRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        servicoContabilOrdemServico.setOrdemServico(ordemServicoBack);
        assertThat(servicoContabilOrdemServico.getOrdemServico()).isEqualTo(ordemServicoBack);

        servicoContabilOrdemServico.ordemServico(null);
        assertThat(servicoContabilOrdemServico.getOrdemServico()).isNull();
    }
}
