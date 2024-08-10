package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalRamoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalRamoDTO.class);
        AdicionalRamoDTO adicionalRamoDTO1 = new AdicionalRamoDTO();
        adicionalRamoDTO1.setId(1L);
        AdicionalRamoDTO adicionalRamoDTO2 = new AdicionalRamoDTO();
        assertThat(adicionalRamoDTO1).isNotEqualTo(adicionalRamoDTO2);
        adicionalRamoDTO2.setId(adicionalRamoDTO1.getId());
        assertThat(adicionalRamoDTO1).isEqualTo(adicionalRamoDTO2);
        adicionalRamoDTO2.setId(2L);
        assertThat(adicionalRamoDTO1).isNotEqualTo(adicionalRamoDTO2);
        adicionalRamoDTO1.setId(null);
        assertThat(adicionalRamoDTO1).isNotEqualTo(adicionalRamoDTO2);
    }
}
