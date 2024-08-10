package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnquadramentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquadramentoDTO.class);
        EnquadramentoDTO enquadramentoDTO1 = new EnquadramentoDTO();
        enquadramentoDTO1.setId(1L);
        EnquadramentoDTO enquadramentoDTO2 = new EnquadramentoDTO();
        assertThat(enquadramentoDTO1).isNotEqualTo(enquadramentoDTO2);
        enquadramentoDTO2.setId(enquadramentoDTO1.getId());
        assertThat(enquadramentoDTO1).isEqualTo(enquadramentoDTO2);
        enquadramentoDTO2.setId(2L);
        assertThat(enquadramentoDTO1).isNotEqualTo(enquadramentoDTO2);
        enquadramentoDTO1.setId(null);
        assertThat(enquadramentoDTO1).isNotEqualTo(enquadramentoDTO2);
    }
}
