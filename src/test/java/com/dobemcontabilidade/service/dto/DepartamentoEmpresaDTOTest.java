package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoEmpresaDTO.class);
        DepartamentoEmpresaDTO departamentoEmpresaDTO1 = new DepartamentoEmpresaDTO();
        departamentoEmpresaDTO1.setId(1L);
        DepartamentoEmpresaDTO departamentoEmpresaDTO2 = new DepartamentoEmpresaDTO();
        assertThat(departamentoEmpresaDTO1).isNotEqualTo(departamentoEmpresaDTO2);
        departamentoEmpresaDTO2.setId(departamentoEmpresaDTO1.getId());
        assertThat(departamentoEmpresaDTO1).isEqualTo(departamentoEmpresaDTO2);
        departamentoEmpresaDTO2.setId(2L);
        assertThat(departamentoEmpresaDTO1).isNotEqualTo(departamentoEmpresaDTO2);
        departamentoEmpresaDTO1.setId(null);
        assertThat(departamentoEmpresaDTO1).isNotEqualTo(departamentoEmpresaDTO2);
    }
}
