package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvaliacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvaliacaoDTO.class);
        AvaliacaoDTO avaliacaoDTO1 = new AvaliacaoDTO();
        avaliacaoDTO1.setId(1L);
        AvaliacaoDTO avaliacaoDTO2 = new AvaliacaoDTO();
        assertThat(avaliacaoDTO1).isNotEqualTo(avaliacaoDTO2);
        avaliacaoDTO2.setId(avaliacaoDTO1.getId());
        assertThat(avaliacaoDTO1).isEqualTo(avaliacaoDTO2);
        avaliacaoDTO2.setId(2L);
        assertThat(avaliacaoDTO1).isNotEqualTo(avaliacaoDTO2);
        avaliacaoDTO1.setId(null);
        assertThat(avaliacaoDTO1).isNotEqualTo(avaliacaoDTO2);
    }
}
