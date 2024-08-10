package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentoTarefaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoTarefaDTO.class);
        DocumentoTarefaDTO documentoTarefaDTO1 = new DocumentoTarefaDTO();
        documentoTarefaDTO1.setId(1L);
        DocumentoTarefaDTO documentoTarefaDTO2 = new DocumentoTarefaDTO();
        assertThat(documentoTarefaDTO1).isNotEqualTo(documentoTarefaDTO2);
        documentoTarefaDTO2.setId(documentoTarefaDTO1.getId());
        assertThat(documentoTarefaDTO1).isEqualTo(documentoTarefaDTO2);
        documentoTarefaDTO2.setId(2L);
        assertThat(documentoTarefaDTO1).isNotEqualTo(documentoTarefaDTO2);
        documentoTarefaDTO1.setId(null);
        assertThat(documentoTarefaDTO1).isNotEqualTo(documentoTarefaDTO2);
    }
}
