package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcaoNomeFantasiaEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpcaoNomeFantasiaEmpresaDTO.class);
        OpcaoNomeFantasiaEmpresaDTO opcaoNomeFantasiaEmpresaDTO1 = new OpcaoNomeFantasiaEmpresaDTO();
        opcaoNomeFantasiaEmpresaDTO1.setId(1L);
        OpcaoNomeFantasiaEmpresaDTO opcaoNomeFantasiaEmpresaDTO2 = new OpcaoNomeFantasiaEmpresaDTO();
        assertThat(opcaoNomeFantasiaEmpresaDTO1).isNotEqualTo(opcaoNomeFantasiaEmpresaDTO2);
        opcaoNomeFantasiaEmpresaDTO2.setId(opcaoNomeFantasiaEmpresaDTO1.getId());
        assertThat(opcaoNomeFantasiaEmpresaDTO1).isEqualTo(opcaoNomeFantasiaEmpresaDTO2);
        opcaoNomeFantasiaEmpresaDTO2.setId(2L);
        assertThat(opcaoNomeFantasiaEmpresaDTO1).isNotEqualTo(opcaoNomeFantasiaEmpresaDTO2);
        opcaoNomeFantasiaEmpresaDTO1.setId(null);
        assertThat(opcaoNomeFantasiaEmpresaDTO1).isNotEqualTo(opcaoNomeFantasiaEmpresaDTO2);
    }
}
