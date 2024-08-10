package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilContadorAreaContabilDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilContadorAreaContabilDTO.class);
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO1 = new PerfilContadorAreaContabilDTO();
        perfilContadorAreaContabilDTO1.setId(1L);
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO2 = new PerfilContadorAreaContabilDTO();
        assertThat(perfilContadorAreaContabilDTO1).isNotEqualTo(perfilContadorAreaContabilDTO2);
        perfilContadorAreaContabilDTO2.setId(perfilContadorAreaContabilDTO1.getId());
        assertThat(perfilContadorAreaContabilDTO1).isEqualTo(perfilContadorAreaContabilDTO2);
        perfilContadorAreaContabilDTO2.setId(2L);
        assertThat(perfilContadorAreaContabilDTO1).isNotEqualTo(perfilContadorAreaContabilDTO2);
        perfilContadorAreaContabilDTO1.setId(null);
        assertThat(perfilContadorAreaContabilDTO1).isNotEqualTo(perfilContadorAreaContabilDTO2);
    }
}
