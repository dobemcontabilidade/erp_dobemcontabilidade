package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvaliacaoContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvaliacaoContadorDTO.class);
        AvaliacaoContadorDTO avaliacaoContadorDTO1 = new AvaliacaoContadorDTO();
        avaliacaoContadorDTO1.setId(1L);
        AvaliacaoContadorDTO avaliacaoContadorDTO2 = new AvaliacaoContadorDTO();
        assertThat(avaliacaoContadorDTO1).isNotEqualTo(avaliacaoContadorDTO2);
        avaliacaoContadorDTO2.setId(avaliacaoContadorDTO1.getId());
        assertThat(avaliacaoContadorDTO1).isEqualTo(avaliacaoContadorDTO2);
        avaliacaoContadorDTO2.setId(2L);
        assertThat(avaliacaoContadorDTO1).isNotEqualTo(avaliacaoContadorDTO2);
        avaliacaoContadorDTO1.setId(null);
        assertThat(avaliacaoContadorDTO1).isNotEqualTo(avaliacaoContadorDTO2);
    }
}
