package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContadorResponsavelTarefaRecorrenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContadorResponsavelTarefaRecorrente.class);
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente1 = getContadorResponsavelTarefaRecorrenteSample1();
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente2 = new ContadorResponsavelTarefaRecorrente();
        assertThat(contadorResponsavelTarefaRecorrente1).isNotEqualTo(contadorResponsavelTarefaRecorrente2);

        contadorResponsavelTarefaRecorrente2.setId(contadorResponsavelTarefaRecorrente1.getId());
        assertThat(contadorResponsavelTarefaRecorrente1).isEqualTo(contadorResponsavelTarefaRecorrente2);

        contadorResponsavelTarefaRecorrente2 = getContadorResponsavelTarefaRecorrenteSample2();
        assertThat(contadorResponsavelTarefaRecorrente1).isNotEqualTo(contadorResponsavelTarefaRecorrente2);
    }

    @Test
    void tarefaRecorrenteExecucaoTest() {
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente =
            getContadorResponsavelTarefaRecorrenteRandomSampleGenerator();
        TarefaRecorrenteExecucao tarefaRecorrenteExecucaoBack = getTarefaRecorrenteExecucaoRandomSampleGenerator();

        contadorResponsavelTarefaRecorrente.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucaoBack);
        assertThat(contadorResponsavelTarefaRecorrente.getTarefaRecorrenteExecucao()).isEqualTo(tarefaRecorrenteExecucaoBack);

        contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao(null);
        assertThat(contadorResponsavelTarefaRecorrente.getTarefaRecorrenteExecucao()).isNull();
    }

    @Test
    void contadorTest() {
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente =
            getContadorResponsavelTarefaRecorrenteRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        contadorResponsavelTarefaRecorrente.setContador(contadorBack);
        assertThat(contadorResponsavelTarefaRecorrente.getContador()).isEqualTo(contadorBack);

        contadorResponsavelTarefaRecorrente.contador(null);
        assertThat(contadorResponsavelTarefaRecorrente.getContador()).isNull();
    }
}
