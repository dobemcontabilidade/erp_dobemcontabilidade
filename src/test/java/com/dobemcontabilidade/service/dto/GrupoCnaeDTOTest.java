package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoCnaeDTO.class);
        GrupoCnaeDTO grupoCnaeDTO1 = new GrupoCnaeDTO();
        grupoCnaeDTO1.setId(1L);
        GrupoCnaeDTO grupoCnaeDTO2 = new GrupoCnaeDTO();
        assertThat(grupoCnaeDTO1).isNotEqualTo(grupoCnaeDTO2);
        grupoCnaeDTO2.setId(grupoCnaeDTO1.getId());
        assertThat(grupoCnaeDTO1).isEqualTo(grupoCnaeDTO2);
        grupoCnaeDTO2.setId(2L);
        assertThat(grupoCnaeDTO1).isNotEqualTo(grupoCnaeDTO2);
        grupoCnaeDTO1.setId(null);
        assertThat(grupoCnaeDTO1).isNotEqualTo(grupoCnaeDTO2);
    }
}
