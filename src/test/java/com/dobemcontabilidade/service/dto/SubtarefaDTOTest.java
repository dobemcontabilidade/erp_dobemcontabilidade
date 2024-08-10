package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubtarefaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubtarefaDTO.class);
        SubtarefaDTO subtarefaDTO1 = new SubtarefaDTO();
        subtarefaDTO1.setId(1L);
        SubtarefaDTO subtarefaDTO2 = new SubtarefaDTO();
        assertThat(subtarefaDTO1).isNotEqualTo(subtarefaDTO2);
        subtarefaDTO2.setId(subtarefaDTO1.getId());
        assertThat(subtarefaDTO1).isEqualTo(subtarefaDTO2);
        subtarefaDTO2.setId(2L);
        assertThat(subtarefaDTO1).isNotEqualTo(subtarefaDTO2);
        subtarefaDTO1.setId(null);
        assertThat(subtarefaDTO1).isNotEqualTo(subtarefaDTO2);
    }
}
