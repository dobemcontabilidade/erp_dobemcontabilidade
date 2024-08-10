package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DocumentoTarefaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentoTarefaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoTarefa.class);
        DocumentoTarefa documentoTarefa1 = getDocumentoTarefaSample1();
        DocumentoTarefa documentoTarefa2 = new DocumentoTarefa();
        assertThat(documentoTarefa1).isNotEqualTo(documentoTarefa2);

        documentoTarefa2.setId(documentoTarefa1.getId());
        assertThat(documentoTarefa1).isEqualTo(documentoTarefa2);

        documentoTarefa2 = getDocumentoTarefaSample2();
        assertThat(documentoTarefa1).isNotEqualTo(documentoTarefa2);
    }

    @Test
    void tarefaTest() {
        DocumentoTarefa documentoTarefa = getDocumentoTarefaRandomSampleGenerator();
        Tarefa tarefaBack = getTarefaRandomSampleGenerator();

        documentoTarefa.setTarefa(tarefaBack);
        assertThat(documentoTarefa.getTarefa()).isEqualTo(tarefaBack);

        documentoTarefa.tarefa(null);
        assertThat(documentoTarefa.getTarefa()).isNull();
    }
}
