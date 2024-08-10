package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioGestaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioGestaoDTO.class);
        UsuarioGestaoDTO usuarioGestaoDTO1 = new UsuarioGestaoDTO();
        usuarioGestaoDTO1.setId(1L);
        UsuarioGestaoDTO usuarioGestaoDTO2 = new UsuarioGestaoDTO();
        assertThat(usuarioGestaoDTO1).isNotEqualTo(usuarioGestaoDTO2);
        usuarioGestaoDTO2.setId(usuarioGestaoDTO1.getId());
        assertThat(usuarioGestaoDTO1).isEqualTo(usuarioGestaoDTO2);
        usuarioGestaoDTO2.setId(2L);
        assertThat(usuarioGestaoDTO1).isNotEqualTo(usuarioGestaoDTO2);
        usuarioGestaoDTO1.setId(null);
        assertThat(usuarioGestaoDTO1).isNotEqualTo(usuarioGestaoDTO2);
    }
}
