package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoContabilDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoContabilDTO.class);
        PlanoContabilDTO planoContabilDTO1 = new PlanoContabilDTO();
        planoContabilDTO1.setId(1L);
        PlanoContabilDTO planoContabilDTO2 = new PlanoContabilDTO();
        assertThat(planoContabilDTO1).isNotEqualTo(planoContabilDTO2);
        planoContabilDTO2.setId(planoContabilDTO1.getId());
        assertThat(planoContabilDTO1).isEqualTo(planoContabilDTO2);
        planoContabilDTO2.setId(2L);
        assertThat(planoContabilDTO1).isNotEqualTo(planoContabilDTO2);
        planoContabilDTO1.setId(null);
        assertThat(planoContabilDTO1).isNotEqualTo(planoContabilDTO2);
    }
}
