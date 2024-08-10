package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecaoCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecaoCnaeDTO.class);
        SecaoCnaeDTO secaoCnaeDTO1 = new SecaoCnaeDTO();
        secaoCnaeDTO1.setId(1L);
        SecaoCnaeDTO secaoCnaeDTO2 = new SecaoCnaeDTO();
        assertThat(secaoCnaeDTO1).isNotEqualTo(secaoCnaeDTO2);
        secaoCnaeDTO2.setId(secaoCnaeDTO1.getId());
        assertThat(secaoCnaeDTO1).isEqualTo(secaoCnaeDTO2);
        secaoCnaeDTO2.setId(2L);
        assertThat(secaoCnaeDTO1).isNotEqualTo(secaoCnaeDTO2);
        secaoCnaeDTO1.setId(null);
        assertThat(secaoCnaeDTO1).isNotEqualTo(secaoCnaeDTO2);
    }
}
