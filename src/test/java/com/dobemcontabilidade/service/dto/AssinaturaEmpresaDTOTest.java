package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssinaturaEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssinaturaEmpresaDTO.class);
        AssinaturaEmpresaDTO assinaturaEmpresaDTO1 = new AssinaturaEmpresaDTO();
        assinaturaEmpresaDTO1.setId(1L);
        AssinaturaEmpresaDTO assinaturaEmpresaDTO2 = new AssinaturaEmpresaDTO();
        assertThat(assinaturaEmpresaDTO1).isNotEqualTo(assinaturaEmpresaDTO2);
        assinaturaEmpresaDTO2.setId(assinaturaEmpresaDTO1.getId());
        assertThat(assinaturaEmpresaDTO1).isEqualTo(assinaturaEmpresaDTO2);
        assinaturaEmpresaDTO2.setId(2L);
        assertThat(assinaturaEmpresaDTO1).isNotEqualTo(assinaturaEmpresaDTO2);
        assinaturaEmpresaDTO1.setId(null);
        assertThat(assinaturaEmpresaDTO1).isNotEqualTo(assinaturaEmpresaDTO2);
    }
}
