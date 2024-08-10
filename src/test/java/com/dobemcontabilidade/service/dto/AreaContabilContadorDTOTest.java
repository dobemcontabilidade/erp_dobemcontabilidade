package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaContabilContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabilContadorDTO.class);
        AreaContabilContadorDTO areaContabilContadorDTO1 = new AreaContabilContadorDTO();
        areaContabilContadorDTO1.setId(1L);
        AreaContabilContadorDTO areaContabilContadorDTO2 = new AreaContabilContadorDTO();
        assertThat(areaContabilContadorDTO1).isNotEqualTo(areaContabilContadorDTO2);
        areaContabilContadorDTO2.setId(areaContabilContadorDTO1.getId());
        assertThat(areaContabilContadorDTO1).isEqualTo(areaContabilContadorDTO2);
        areaContabilContadorDTO2.setId(2L);
        assertThat(areaContabilContadorDTO1).isNotEqualTo(areaContabilContadorDTO2);
        areaContabilContadorDTO1.setId(null);
        assertThat(areaContabilContadorDTO1).isNotEqualTo(areaContabilContadorDTO2);
    }
}
