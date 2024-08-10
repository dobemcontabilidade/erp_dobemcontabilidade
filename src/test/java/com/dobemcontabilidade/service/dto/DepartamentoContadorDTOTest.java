package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoContadorDTO.class);
        DepartamentoContadorDTO departamentoContadorDTO1 = new DepartamentoContadorDTO();
        departamentoContadorDTO1.setId(1L);
        DepartamentoContadorDTO departamentoContadorDTO2 = new DepartamentoContadorDTO();
        assertThat(departamentoContadorDTO1).isNotEqualTo(departamentoContadorDTO2);
        departamentoContadorDTO2.setId(departamentoContadorDTO1.getId());
        assertThat(departamentoContadorDTO1).isEqualTo(departamentoContadorDTO2);
        departamentoContadorDTO2.setId(2L);
        assertThat(departamentoContadorDTO1).isNotEqualTo(departamentoContadorDTO2);
        departamentoContadorDTO1.setId(null);
        assertThat(departamentoContadorDTO1).isNotEqualTo(departamentoContadorDTO2);
    }
}
