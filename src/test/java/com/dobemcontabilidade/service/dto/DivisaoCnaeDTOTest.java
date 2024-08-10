package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DivisaoCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DivisaoCnaeDTO.class);
        DivisaoCnaeDTO divisaoCnaeDTO1 = new DivisaoCnaeDTO();
        divisaoCnaeDTO1.setId(1L);
        DivisaoCnaeDTO divisaoCnaeDTO2 = new DivisaoCnaeDTO();
        assertThat(divisaoCnaeDTO1).isNotEqualTo(divisaoCnaeDTO2);
        divisaoCnaeDTO2.setId(divisaoCnaeDTO1.getId());
        assertThat(divisaoCnaeDTO1).isEqualTo(divisaoCnaeDTO2);
        divisaoCnaeDTO2.setId(2L);
        assertThat(divisaoCnaeDTO1).isNotEqualTo(divisaoCnaeDTO2);
        divisaoCnaeDTO1.setId(null);
        assertThat(divisaoCnaeDTO1).isNotEqualTo(divisaoCnaeDTO2);
    }
}
