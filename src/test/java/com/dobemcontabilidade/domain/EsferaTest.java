package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EsferaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
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
    void servicoContabilTest() {
        Esfera esfera = getEsferaRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        esfera.addServicoContabil(servicoContabilBack);
        assertThat(esfera.getServicoContabils()).containsOnly(servicoContabilBack);
        assertThat(servicoContabilBack.getEsfera()).isEqualTo(esfera);

        esfera.removeServicoContabil(servicoContabilBack);
        assertThat(esfera.getServicoContabils()).doesNotContain(servicoContabilBack);
        assertThat(servicoContabilBack.getEsfera()).isNull();

        esfera.servicoContabils(new HashSet<>(Set.of(servicoContabilBack)));
        assertThat(esfera.getServicoContabils()).containsOnly(servicoContabilBack);
        assertThat(servicoContabilBack.getEsfera()).isEqualTo(esfera);

        esfera.setServicoContabils(new HashSet<>());
        assertThat(esfera.getServicoContabils()).doesNotContain(servicoContabilBack);
        assertThat(servicoContabilBack.getEsfera()).isNull();
    }

    @Test
    void impostoTest() {
        Esfera esfera = getEsferaRandomSampleGenerator();
        Imposto impostoBack = getImpostoRandomSampleGenerator();

        esfera.addImposto(impostoBack);
        assertThat(esfera.getImpostos()).containsOnly(impostoBack);
        assertThat(impostoBack.getEsfera()).isEqualTo(esfera);

        esfera.removeImposto(impostoBack);
        assertThat(esfera.getImpostos()).doesNotContain(impostoBack);
        assertThat(impostoBack.getEsfera()).isNull();

        esfera.impostos(new HashSet<>(Set.of(impostoBack)));
        assertThat(esfera.getImpostos()).containsOnly(impostoBack);
        assertThat(impostoBack.getEsfera()).isEqualTo(esfera);

        esfera.setImpostos(new HashSet<>());
        assertThat(esfera.getImpostos()).doesNotContain(impostoBack);
        assertThat(impostoBack.getEsfera()).isNull();
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
