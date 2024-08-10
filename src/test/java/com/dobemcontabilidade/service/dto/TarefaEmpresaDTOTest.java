package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarefaEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaEmpresaDTO.class);
        TarefaEmpresaDTO tarefaEmpresaDTO1 = new TarefaEmpresaDTO();
        tarefaEmpresaDTO1.setId(1L);
        TarefaEmpresaDTO tarefaEmpresaDTO2 = new TarefaEmpresaDTO();
        assertThat(tarefaEmpresaDTO1).isNotEqualTo(tarefaEmpresaDTO2);
        tarefaEmpresaDTO2.setId(tarefaEmpresaDTO1.getId());
        assertThat(tarefaEmpresaDTO1).isEqualTo(tarefaEmpresaDTO2);
        tarefaEmpresaDTO2.setId(2L);
        assertThat(tarefaEmpresaDTO1).isNotEqualTo(tarefaEmpresaDTO2);
        tarefaEmpresaDTO1.setId(null);
        assertThat(tarefaEmpresaDTO1).isNotEqualTo(tarefaEmpresaDTO2);
    }
}
