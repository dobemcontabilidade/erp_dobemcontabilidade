package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaOrdemServicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoRequeridoTarefaOrdemServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoRequeridoTarefaOrdemServico.class);
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico1 = getAnexoRequeridoTarefaOrdemServicoSample1();
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico2 = new AnexoRequeridoTarefaOrdemServico();
        assertThat(anexoRequeridoTarefaOrdemServico1).isNotEqualTo(anexoRequeridoTarefaOrdemServico2);

        anexoRequeridoTarefaOrdemServico2.setId(anexoRequeridoTarefaOrdemServico1.getId());
        assertThat(anexoRequeridoTarefaOrdemServico1).isEqualTo(anexoRequeridoTarefaOrdemServico2);

        anexoRequeridoTarefaOrdemServico2 = getAnexoRequeridoTarefaOrdemServicoSample2();
        assertThat(anexoRequeridoTarefaOrdemServico1).isNotEqualTo(anexoRequeridoTarefaOrdemServico2);
    }

    @Test
    void anexoRequeridoTest() {
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico = getAnexoRequeridoTarefaOrdemServicoRandomSampleGenerator();
        AnexoRequerido anexoRequeridoBack = getAnexoRequeridoRandomSampleGenerator();

        anexoRequeridoTarefaOrdemServico.setAnexoRequerido(anexoRequeridoBack);
        assertThat(anexoRequeridoTarefaOrdemServico.getAnexoRequerido()).isEqualTo(anexoRequeridoBack);

        anexoRequeridoTarefaOrdemServico.anexoRequerido(null);
        assertThat(anexoRequeridoTarefaOrdemServico.getAnexoRequerido()).isNull();
    }

    @Test
    void tarefaOrdemServicoTest() {
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico = getAnexoRequeridoTarefaOrdemServicoRandomSampleGenerator();
        TarefaOrdemServico tarefaOrdemServicoBack = getTarefaOrdemServicoRandomSampleGenerator();

        anexoRequeridoTarefaOrdemServico.setTarefaOrdemServico(tarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServico.getTarefaOrdemServico()).isEqualTo(tarefaOrdemServicoBack);

        anexoRequeridoTarefaOrdemServico.tarefaOrdemServico(null);
        assertThat(anexoRequeridoTarefaOrdemServico.getTarefaOrdemServico()).isNull();
    }
}
