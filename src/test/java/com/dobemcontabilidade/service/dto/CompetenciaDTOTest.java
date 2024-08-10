package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetenciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetenciaDTO.class);
        CompetenciaDTO competenciaDTO1 = new CompetenciaDTO();
        competenciaDTO1.setId(1L);
        CompetenciaDTO competenciaDTO2 = new CompetenciaDTO();
        assertThat(competenciaDTO1).isNotEqualTo(competenciaDTO2);
        competenciaDTO2.setId(competenciaDTO1.getId());
        assertThat(competenciaDTO1).isEqualTo(competenciaDTO2);
        competenciaDTO2.setId(2L);
        assertThat(competenciaDTO1).isNotEqualTo(competenciaDTO2);
        competenciaDTO1.setId(null);
        assertThat(competenciaDTO1).isNotEqualTo(competenciaDTO2);
    }
}
