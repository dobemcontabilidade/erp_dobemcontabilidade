package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoEmpresaDTO.class);
        AnexoEmpresaDTO anexoEmpresaDTO1 = new AnexoEmpresaDTO();
        anexoEmpresaDTO1.setId(1L);
        AnexoEmpresaDTO anexoEmpresaDTO2 = new AnexoEmpresaDTO();
        assertThat(anexoEmpresaDTO1).isNotEqualTo(anexoEmpresaDTO2);
        anexoEmpresaDTO2.setId(anexoEmpresaDTO1.getId());
        assertThat(anexoEmpresaDTO1).isEqualTo(anexoEmpresaDTO2);
        anexoEmpresaDTO2.setId(2L);
        assertThat(anexoEmpresaDTO1).isNotEqualTo(anexoEmpresaDTO2);
        anexoEmpresaDTO1.setId(null);
        assertThat(anexoEmpresaDTO1).isNotEqualTo(anexoEmpresaDTO2);
    }
}
