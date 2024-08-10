package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermoDeAdesaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoDeAdesaoDTO.class);
        TermoDeAdesaoDTO termoDeAdesaoDTO1 = new TermoDeAdesaoDTO();
        termoDeAdesaoDTO1.setId(1L);
        TermoDeAdesaoDTO termoDeAdesaoDTO2 = new TermoDeAdesaoDTO();
        assertThat(termoDeAdesaoDTO1).isNotEqualTo(termoDeAdesaoDTO2);
        termoDeAdesaoDTO2.setId(termoDeAdesaoDTO1.getId());
        assertThat(termoDeAdesaoDTO1).isEqualTo(termoDeAdesaoDTO2);
        termoDeAdesaoDTO2.setId(2L);
        assertThat(termoDeAdesaoDTO1).isNotEqualTo(termoDeAdesaoDTO2);
        termoDeAdesaoDTO1.setId(null);
        assertThat(termoDeAdesaoDTO1).isNotEqualTo(termoDeAdesaoDTO2);
    }
}
