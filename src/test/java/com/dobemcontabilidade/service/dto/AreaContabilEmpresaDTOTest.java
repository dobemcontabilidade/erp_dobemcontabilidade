package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaContabilEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabilEmpresaDTO.class);
        AreaContabilEmpresaDTO areaContabilEmpresaDTO1 = new AreaContabilEmpresaDTO();
        areaContabilEmpresaDTO1.setId(1L);
        AreaContabilEmpresaDTO areaContabilEmpresaDTO2 = new AreaContabilEmpresaDTO();
        assertThat(areaContabilEmpresaDTO1).isNotEqualTo(areaContabilEmpresaDTO2);
        areaContabilEmpresaDTO2.setId(areaContabilEmpresaDTO1.getId());
        assertThat(areaContabilEmpresaDTO1).isEqualTo(areaContabilEmpresaDTO2);
        areaContabilEmpresaDTO2.setId(2L);
        assertThat(areaContabilEmpresaDTO1).isNotEqualTo(areaContabilEmpresaDTO2);
        areaContabilEmpresaDTO1.setId(null);
        assertThat(areaContabilEmpresaDTO1).isNotEqualTo(areaContabilEmpresaDTO2);
    }
}
