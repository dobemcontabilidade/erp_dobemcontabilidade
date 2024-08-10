package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DescontoPlanoContaAzulDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoPlanoContaAzulDTO.class);
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO1 = new DescontoPlanoContaAzulDTO();
        descontoPlanoContaAzulDTO1.setId(1L);
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO2 = new DescontoPlanoContaAzulDTO();
        assertThat(descontoPlanoContaAzulDTO1).isNotEqualTo(descontoPlanoContaAzulDTO2);
        descontoPlanoContaAzulDTO2.setId(descontoPlanoContaAzulDTO1.getId());
        assertThat(descontoPlanoContaAzulDTO1).isEqualTo(descontoPlanoContaAzulDTO2);
        descontoPlanoContaAzulDTO2.setId(2L);
        assertThat(descontoPlanoContaAzulDTO1).isNotEqualTo(descontoPlanoContaAzulDTO2);
        descontoPlanoContaAzulDTO1.setId(null);
        assertThat(descontoPlanoContaAzulDTO1).isNotEqualTo(descontoPlanoContaAzulDTO2);
    }
}
