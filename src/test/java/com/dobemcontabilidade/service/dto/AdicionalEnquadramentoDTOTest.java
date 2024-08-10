package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalEnquadramentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalEnquadramentoDTO.class);
        AdicionalEnquadramentoDTO adicionalEnquadramentoDTO1 = new AdicionalEnquadramentoDTO();
        adicionalEnquadramentoDTO1.setId(1L);
        AdicionalEnquadramentoDTO adicionalEnquadramentoDTO2 = new AdicionalEnquadramentoDTO();
        assertThat(adicionalEnquadramentoDTO1).isNotEqualTo(adicionalEnquadramentoDTO2);
        adicionalEnquadramentoDTO2.setId(adicionalEnquadramentoDTO1.getId());
        assertThat(adicionalEnquadramentoDTO1).isEqualTo(adicionalEnquadramentoDTO2);
        adicionalEnquadramentoDTO2.setId(2L);
        assertThat(adicionalEnquadramentoDTO1).isNotEqualTo(adicionalEnquadramentoDTO2);
        adicionalEnquadramentoDTO1.setId(null);
        assertThat(adicionalEnquadramentoDTO1).isNotEqualTo(adicionalEnquadramentoDTO2);
    }
}
