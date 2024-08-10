package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaContabilDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabilDTO.class);
        AreaContabilDTO areaContabilDTO1 = new AreaContabilDTO();
        areaContabilDTO1.setId(1L);
        AreaContabilDTO areaContabilDTO2 = new AreaContabilDTO();
        assertThat(areaContabilDTO1).isNotEqualTo(areaContabilDTO2);
        areaContabilDTO2.setId(areaContabilDTO1.getId());
        assertThat(areaContabilDTO1).isEqualTo(areaContabilDTO2);
        areaContabilDTO2.setId(2L);
        assertThat(areaContabilDTO1).isNotEqualTo(areaContabilDTO2);
        areaContabilDTO1.setId(null);
        assertThat(areaContabilDTO1).isNotEqualTo(areaContabilDTO2);
    }
}
