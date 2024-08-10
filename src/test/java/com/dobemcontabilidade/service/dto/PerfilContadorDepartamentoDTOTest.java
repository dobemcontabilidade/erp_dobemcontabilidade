package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilContadorDepartamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilContadorDepartamentoDTO.class);
        PerfilContadorDepartamentoDTO perfilContadorDepartamentoDTO1 = new PerfilContadorDepartamentoDTO();
        perfilContadorDepartamentoDTO1.setId(1L);
        PerfilContadorDepartamentoDTO perfilContadorDepartamentoDTO2 = new PerfilContadorDepartamentoDTO();
        assertThat(perfilContadorDepartamentoDTO1).isNotEqualTo(perfilContadorDepartamentoDTO2);
        perfilContadorDepartamentoDTO2.setId(perfilContadorDepartamentoDTO1.getId());
        assertThat(perfilContadorDepartamentoDTO1).isEqualTo(perfilContadorDepartamentoDTO2);
        perfilContadorDepartamentoDTO2.setId(2L);
        assertThat(perfilContadorDepartamentoDTO1).isNotEqualTo(perfilContadorDepartamentoDTO2);
        perfilContadorDepartamentoDTO1.setId(null);
        assertThat(perfilContadorDepartamentoDTO1).isNotEqualTo(perfilContadorDepartamentoDTO2);
    }
}
