package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CnaeDTO.class);
        CnaeDTO cnaeDTO1 = new CnaeDTO();
        cnaeDTO1.setId(1L);
        CnaeDTO cnaeDTO2 = new CnaeDTO();
        assertThat(cnaeDTO1).isNotEqualTo(cnaeDTO2);
        cnaeDTO2.setId(cnaeDTO1.getId());
        assertThat(cnaeDTO1).isEqualTo(cnaeDTO2);
        cnaeDTO2.setId(2L);
        assertThat(cnaeDTO1).isNotEqualTo(cnaeDTO2);
        cnaeDTO1.setId(null);
        assertThat(cnaeDTO1).isNotEqualTo(cnaeDTO2);
    }
}
