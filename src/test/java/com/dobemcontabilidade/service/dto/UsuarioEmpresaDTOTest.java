package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioEmpresaDTO.class);
        UsuarioEmpresaDTO usuarioEmpresaDTO1 = new UsuarioEmpresaDTO();
        usuarioEmpresaDTO1.setId(1L);
        UsuarioEmpresaDTO usuarioEmpresaDTO2 = new UsuarioEmpresaDTO();
        assertThat(usuarioEmpresaDTO1).isNotEqualTo(usuarioEmpresaDTO2);
        usuarioEmpresaDTO2.setId(usuarioEmpresaDTO1.getId());
        assertThat(usuarioEmpresaDTO1).isEqualTo(usuarioEmpresaDTO2);
        usuarioEmpresaDTO2.setId(2L);
        assertThat(usuarioEmpresaDTO1).isNotEqualTo(usuarioEmpresaDTO2);
        usuarioEmpresaDTO1.setId(null);
        assertThat(usuarioEmpresaDTO1).isNotEqualTo(usuarioEmpresaDTO2);
    }
}
