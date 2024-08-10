package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilContadorDTO.class);
        PerfilContadorDTO perfilContadorDTO1 = new PerfilContadorDTO();
        perfilContadorDTO1.setId(1L);
        PerfilContadorDTO perfilContadorDTO2 = new PerfilContadorDTO();
        assertThat(perfilContadorDTO1).isNotEqualTo(perfilContadorDTO2);
        perfilContadorDTO2.setId(perfilContadorDTO1.getId());
        assertThat(perfilContadorDTO1).isEqualTo(perfilContadorDTO2);
        perfilContadorDTO2.setId(2L);
        assertThat(perfilContadorDTO1).isNotEqualTo(perfilContadorDTO2);
        perfilContadorDTO1.setId(null);
        assertThat(perfilContadorDTO1).isNotEqualTo(perfilContadorDTO2);
    }
}
