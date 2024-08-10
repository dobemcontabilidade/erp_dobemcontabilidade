package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoFuncionarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoFuncionarioDTO.class);
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO1 = new DepartamentoFuncionarioDTO();
        departamentoFuncionarioDTO1.setId(1L);
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO2 = new DepartamentoFuncionarioDTO();
        assertThat(departamentoFuncionarioDTO1).isNotEqualTo(departamentoFuncionarioDTO2);
        departamentoFuncionarioDTO2.setId(departamentoFuncionarioDTO1.getId());
        assertThat(departamentoFuncionarioDTO1).isEqualTo(departamentoFuncionarioDTO2);
        departamentoFuncionarioDTO2.setId(2L);
        assertThat(departamentoFuncionarioDTO1).isNotEqualTo(departamentoFuncionarioDTO2);
        departamentoFuncionarioDTO1.setId(null);
        assertThat(departamentoFuncionarioDTO1).isNotEqualTo(departamentoFuncionarioDTO2);
    }
}
