package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcaoRazaoSocialEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpcaoRazaoSocialEmpresaDTO.class);
        OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO1 = new OpcaoRazaoSocialEmpresaDTO();
        opcaoRazaoSocialEmpresaDTO1.setId(1L);
        OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO2 = new OpcaoRazaoSocialEmpresaDTO();
        assertThat(opcaoRazaoSocialEmpresaDTO1).isNotEqualTo(opcaoRazaoSocialEmpresaDTO2);
        opcaoRazaoSocialEmpresaDTO2.setId(opcaoRazaoSocialEmpresaDTO1.getId());
        assertThat(opcaoRazaoSocialEmpresaDTO1).isEqualTo(opcaoRazaoSocialEmpresaDTO2);
        opcaoRazaoSocialEmpresaDTO2.setId(2L);
        assertThat(opcaoRazaoSocialEmpresaDTO1).isNotEqualTo(opcaoRazaoSocialEmpresaDTO2);
        opcaoRazaoSocialEmpresaDTO1.setId(null);
        assertThat(opcaoRazaoSocialEmpresaDTO1).isNotEqualTo(opcaoRazaoSocialEmpresaDTO2);
    }
}
