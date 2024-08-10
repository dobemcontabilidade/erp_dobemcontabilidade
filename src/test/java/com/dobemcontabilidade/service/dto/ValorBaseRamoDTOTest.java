package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValorBaseRamoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValorBaseRamoDTO.class);
        ValorBaseRamoDTO valorBaseRamoDTO1 = new ValorBaseRamoDTO();
        valorBaseRamoDTO1.setId(1L);
        ValorBaseRamoDTO valorBaseRamoDTO2 = new ValorBaseRamoDTO();
        assertThat(valorBaseRamoDTO1).isNotEqualTo(valorBaseRamoDTO2);
        valorBaseRamoDTO2.setId(valorBaseRamoDTO1.getId());
        assertThat(valorBaseRamoDTO1).isEqualTo(valorBaseRamoDTO2);
        valorBaseRamoDTO2.setId(2L);
        assertThat(valorBaseRamoDTO1).isNotEqualTo(valorBaseRamoDTO2);
        valorBaseRamoDTO1.setId(null);
        assertThat(valorBaseRamoDTO1).isNotEqualTo(valorBaseRamoDTO2);
    }
}
