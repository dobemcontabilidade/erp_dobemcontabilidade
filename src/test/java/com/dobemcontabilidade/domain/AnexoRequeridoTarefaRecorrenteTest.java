package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoRequeridoTarefaRecorrenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoRequeridoTarefaRecorrente.class);
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente1 = getAnexoRequeridoTarefaRecorrenteSample1();
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente2 = new AnexoRequeridoTarefaRecorrente();
        assertThat(anexoRequeridoTarefaRecorrente1).isNotEqualTo(anexoRequeridoTarefaRecorrente2);

        anexoRequeridoTarefaRecorrente2.setId(anexoRequeridoTarefaRecorrente1.getId());
        assertThat(anexoRequeridoTarefaRecorrente1).isEqualTo(anexoRequeridoTarefaRecorrente2);

        anexoRequeridoTarefaRecorrente2 = getAnexoRequeridoTarefaRecorrenteSample2();
        assertThat(anexoRequeridoTarefaRecorrente1).isNotEqualTo(anexoRequeridoTarefaRecorrente2);
    }

    @Test
    void anexoRequeridoTest() {
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente = getAnexoRequeridoTarefaRecorrenteRandomSampleGenerator();
        AnexoRequerido anexoRequeridoBack = getAnexoRequeridoRandomSampleGenerator();

        anexoRequeridoTarefaRecorrente.setAnexoRequerido(anexoRequeridoBack);
        assertThat(anexoRequeridoTarefaRecorrente.getAnexoRequerido()).isEqualTo(anexoRequeridoBack);

        anexoRequeridoTarefaRecorrente.anexoRequerido(null);
        assertThat(anexoRequeridoTarefaRecorrente.getAnexoRequerido()).isNull();
    }

    @Test
    void tarefaRecorrenteTest() {
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente = getAnexoRequeridoTarefaRecorrenteRandomSampleGenerator();
        TarefaRecorrente tarefaRecorrenteBack = getTarefaRecorrenteRandomSampleGenerator();

        anexoRequeridoTarefaRecorrente.setTarefaRecorrente(tarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrente.getTarefaRecorrente()).isEqualTo(tarefaRecorrenteBack);

        anexoRequeridoTarefaRecorrente.tarefaRecorrente(null);
        assertThat(anexoRequeridoTarefaRecorrente.getTarefaRecorrente()).isNull();
    }
}
