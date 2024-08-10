package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoContaAzulDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoContaAzulDTO.class);
        PlanoContaAzulDTO planoContaAzulDTO1 = new PlanoContaAzulDTO();
        planoContaAzulDTO1.setId(1L);
        PlanoContaAzulDTO planoContaAzulDTO2 = new PlanoContaAzulDTO();
        assertThat(planoContaAzulDTO1).isNotEqualTo(planoContaAzulDTO2);
        planoContaAzulDTO2.setId(planoContaAzulDTO1.getId());
        assertThat(planoContaAzulDTO1).isEqualTo(planoContaAzulDTO2);
        planoContaAzulDTO2.setId(2L);
        assertThat(planoContaAzulDTO1).isNotEqualTo(planoContaAzulDTO2);
        planoContaAzulDTO1.setId(null);
        assertThat(planoContaAzulDTO1).isNotEqualTo(planoContaAzulDTO2);
    }
}
