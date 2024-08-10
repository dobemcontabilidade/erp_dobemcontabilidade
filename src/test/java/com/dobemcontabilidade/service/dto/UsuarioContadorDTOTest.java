package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioContadorDTO.class);
        UsuarioContadorDTO usuarioContadorDTO1 = new UsuarioContadorDTO();
        usuarioContadorDTO1.setId(1L);
        UsuarioContadorDTO usuarioContadorDTO2 = new UsuarioContadorDTO();
        assertThat(usuarioContadorDTO1).isNotEqualTo(usuarioContadorDTO2);
        usuarioContadorDTO2.setId(usuarioContadorDTO1.getId());
        assertThat(usuarioContadorDTO1).isEqualTo(usuarioContadorDTO2);
        usuarioContadorDTO2.setId(2L);
        assertThat(usuarioContadorDTO1).isNotEqualTo(usuarioContadorDTO2);
        usuarioContadorDTO1.setId(null);
        assertThat(usuarioContadorDTO1).isNotEqualTo(usuarioContadorDTO2);
    }
}
