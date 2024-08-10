package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtividadeEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtividadeEmpresaDTO.class);
        AtividadeEmpresaDTO atividadeEmpresaDTO1 = new AtividadeEmpresaDTO();
        atividadeEmpresaDTO1.setId(1L);
        AtividadeEmpresaDTO atividadeEmpresaDTO2 = new AtividadeEmpresaDTO();
        assertThat(atividadeEmpresaDTO1).isNotEqualTo(atividadeEmpresaDTO2);
        atividadeEmpresaDTO2.setId(atividadeEmpresaDTO1.getId());
        assertThat(atividadeEmpresaDTO1).isEqualTo(atividadeEmpresaDTO2);
        atividadeEmpresaDTO2.setId(2L);
        assertThat(atividadeEmpresaDTO1).isNotEqualTo(atividadeEmpresaDTO2);
        atividadeEmpresaDTO1.setId(null);
        assertThat(atividadeEmpresaDTO1).isNotEqualTo(atividadeEmpresaDTO2);
    }
}
