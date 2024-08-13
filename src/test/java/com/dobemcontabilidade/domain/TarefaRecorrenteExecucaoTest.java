package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.SubTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TarefaRecorrenteExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaRecorrenteExecucao.class);
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao1 = getTarefaRecorrenteExecucaoSample1();
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao2 = new TarefaRecorrenteExecucao();
        assertThat(tarefaRecorrenteExecucao1).isNotEqualTo(tarefaRecorrenteExecucao2);

        tarefaRecorrenteExecucao2.setId(tarefaRecorrenteExecucao1.getId());
        assertThat(tarefaRecorrenteExecucao1).isEqualTo(tarefaRecorrenteExecucao2);

        tarefaRecorrenteExecucao2 = getTarefaRecorrenteExecucaoSample2();
        assertThat(tarefaRecorrenteExecucao1).isNotEqualTo(tarefaRecorrenteExecucao2);
    }

    @Test
    void agendaTarefaRecorrenteExecucaoTest() {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = getTarefaRecorrenteExecucaoRandomSampleGenerator();
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucaoBack = getAgendaTarefaRecorrenteExecucaoRandomSampleGenerator();

        tarefaRecorrenteExecucao.addAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucao.getAgendaTarefaRecorrenteExecucaos()).containsOnly(agendaTarefaRecorrenteExecucaoBack);
        assertThat(agendaTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.removeAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucao.getAgendaTarefaRecorrenteExecucaos()).doesNotContain(agendaTarefaRecorrenteExecucaoBack);
        assertThat(agendaTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isNull();

        tarefaRecorrenteExecucao.agendaTarefaRecorrenteExecucaos(new HashSet<>(Set.of(agendaTarefaRecorrenteExecucaoBack)));
        assertThat(tarefaRecorrenteExecucao.getAgendaTarefaRecorrenteExecucaos()).containsOnly(agendaTarefaRecorrenteExecucaoBack);
        assertThat(agendaTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.setAgendaTarefaRecorrenteExecucaos(new HashSet<>());
        assertThat(tarefaRecorrenteExecucao.getAgendaTarefaRecorrenteExecucaos()).doesNotContain(agendaTarefaRecorrenteExecucaoBack);
        assertThat(agendaTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void anexoTarefaRecorrenteExecucaoTest() {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = getTarefaRecorrenteExecucaoRandomSampleGenerator();
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucaoBack = getAnexoTarefaRecorrenteExecucaoRandomSampleGenerator();

        tarefaRecorrenteExecucao.addAnexoTarefaRecorrenteExecucao(anexoTarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucao.getAnexoTarefaRecorrenteExecucaos()).containsOnly(anexoTarefaRecorrenteExecucaoBack);
        assertThat(anexoTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.removeAnexoTarefaRecorrenteExecucao(anexoTarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucao.getAnexoTarefaRecorrenteExecucaos()).doesNotContain(anexoTarefaRecorrenteExecucaoBack);
        assertThat(anexoTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isNull();

        tarefaRecorrenteExecucao.anexoTarefaRecorrenteExecucaos(new HashSet<>(Set.of(anexoTarefaRecorrenteExecucaoBack)));
        assertThat(tarefaRecorrenteExecucao.getAnexoTarefaRecorrenteExecucaos()).containsOnly(anexoTarefaRecorrenteExecucaoBack);
        assertThat(anexoTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.setAnexoTarefaRecorrenteExecucaos(new HashSet<>());
        assertThat(tarefaRecorrenteExecucao.getAnexoTarefaRecorrenteExecucaos()).doesNotContain(anexoTarefaRecorrenteExecucaoBack);
        assertThat(anexoTarefaRecorrenteExecucaoBack.getTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void subTarefaRecorrenteTest() {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = getTarefaRecorrenteExecucaoRandomSampleGenerator();
        SubTarefaRecorrente subTarefaRecorrenteBack = getSubTarefaRecorrenteRandomSampleGenerator();

        tarefaRecorrenteExecucao.addSubTarefaRecorrente(subTarefaRecorrenteBack);
        assertThat(tarefaRecorrenteExecucao.getSubTarefaRecorrentes()).containsOnly(subTarefaRecorrenteBack);
        assertThat(subTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.removeSubTarefaRecorrente(subTarefaRecorrenteBack);
        assertThat(tarefaRecorrenteExecucao.getSubTarefaRecorrentes()).doesNotContain(subTarefaRecorrenteBack);
        assertThat(subTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isNull();

        tarefaRecorrenteExecucao.subTarefaRecorrentes(new HashSet<>(Set.of(subTarefaRecorrenteBack)));
        assertThat(tarefaRecorrenteExecucao.getSubTarefaRecorrentes()).containsOnly(subTarefaRecorrenteBack);
        assertThat(subTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.setSubTarefaRecorrentes(new HashSet<>());
        assertThat(tarefaRecorrenteExecucao.getSubTarefaRecorrentes()).doesNotContain(subTarefaRecorrenteBack);
        assertThat(subTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void contadorResponsavelTarefaRecorrenteTest() {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = getTarefaRecorrenteExecucaoRandomSampleGenerator();
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrenteBack =
            getContadorResponsavelTarefaRecorrenteRandomSampleGenerator();

        tarefaRecorrenteExecucao.addContadorResponsavelTarefaRecorrente(contadorResponsavelTarefaRecorrenteBack);
        assertThat(tarefaRecorrenteExecucao.getContadorResponsavelTarefaRecorrentes()).containsOnly(
            contadorResponsavelTarefaRecorrenteBack
        );
        assertThat(contadorResponsavelTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.removeContadorResponsavelTarefaRecorrente(contadorResponsavelTarefaRecorrenteBack);
        assertThat(tarefaRecorrenteExecucao.getContadorResponsavelTarefaRecorrentes()).doesNotContain(
            contadorResponsavelTarefaRecorrenteBack
        );
        assertThat(contadorResponsavelTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isNull();

        tarefaRecorrenteExecucao.contadorResponsavelTarefaRecorrentes(new HashSet<>(Set.of(contadorResponsavelTarefaRecorrenteBack)));
        assertThat(tarefaRecorrenteExecucao.getContadorResponsavelTarefaRecorrentes()).containsOnly(
            contadorResponsavelTarefaRecorrenteBack
        );
        assertThat(contadorResponsavelTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucao);

        tarefaRecorrenteExecucao.setContadorResponsavelTarefaRecorrentes(new HashSet<>());
        assertThat(tarefaRecorrenteExecucao.getContadorResponsavelTarefaRecorrentes()).doesNotContain(
            contadorResponsavelTarefaRecorrenteBack
        );
        assertThat(contadorResponsavelTarefaRecorrenteBack.getTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void tarefaRecorrenteTest() {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = getTarefaRecorrenteExecucaoRandomSampleGenerator();
        TarefaRecorrente tarefaRecorrenteBack = getTarefaRecorrenteRandomSampleGenerator();

        tarefaRecorrenteExecucao.setTarefaRecorrente(tarefaRecorrenteBack);
        assertThat(tarefaRecorrenteExecucao.getTarefaRecorrente()).isEqualTo(tarefaRecorrenteBack);

        tarefaRecorrenteExecucao.tarefaRecorrente(null);
        assertThat(tarefaRecorrenteExecucao.getTarefaRecorrente()).isNull();
    }
}
