package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermoAdesaoContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoAdesaoContadorDTO.class);
        TermoAdesaoContadorDTO termoAdesaoContadorDTO1 = new TermoAdesaoContadorDTO();
        termoAdesaoContadorDTO1.setId(1L);
        TermoAdesaoContadorDTO termoAdesaoContadorDTO2 = new TermoAdesaoContadorDTO();
        assertThat(termoAdesaoContadorDTO1).isNotEqualTo(termoAdesaoContadorDTO2);
        termoAdesaoContadorDTO2.setId(termoAdesaoContadorDTO1.getId());
        assertThat(termoAdesaoContadorDTO1).isEqualTo(termoAdesaoContadorDTO2);
        termoAdesaoContadorDTO2.setId(2L);
        assertThat(termoAdesaoContadorDTO1).isNotEqualTo(termoAdesaoContadorDTO2);
        termoAdesaoContadorDTO1.setId(null);
        assertThat(termoAdesaoContadorDTO1).isNotEqualTo(termoAdesaoContadorDTO2);
    }
}
