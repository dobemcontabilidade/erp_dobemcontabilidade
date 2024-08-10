package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioErpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioErpDTO.class);
        UsuarioErpDTO usuarioErpDTO1 = new UsuarioErpDTO();
        usuarioErpDTO1.setId(1L);
        UsuarioErpDTO usuarioErpDTO2 = new UsuarioErpDTO();
        assertThat(usuarioErpDTO1).isNotEqualTo(usuarioErpDTO2);
        usuarioErpDTO2.setId(usuarioErpDTO1.getId());
        assertThat(usuarioErpDTO1).isEqualTo(usuarioErpDTO2);
        usuarioErpDTO2.setId(2L);
        assertThat(usuarioErpDTO1).isNotEqualTo(usuarioErpDTO2);
        usuarioErpDTO1.setId(null);
        assertThat(usuarioErpDTO1).isNotEqualTo(usuarioErpDTO2);
    }
}
