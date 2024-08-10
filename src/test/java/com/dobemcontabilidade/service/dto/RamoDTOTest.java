package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RamoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RamoDTO.class);
        RamoDTO ramoDTO1 = new RamoDTO();
        ramoDTO1.setId(1L);
        RamoDTO ramoDTO2 = new RamoDTO();
        assertThat(ramoDTO1).isNotEqualTo(ramoDTO2);
        ramoDTO2.setId(ramoDTO1.getId());
        assertThat(ramoDTO1).isEqualTo(ramoDTO2);
        ramoDTO2.setId(2L);
        assertThat(ramoDTO1).isNotEqualTo(ramoDTO2);
        ramoDTO1.setId(null);
        assertThat(ramoDTO1).isNotEqualTo(ramoDTO2);
    }
}
