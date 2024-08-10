package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BancoContadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BancoContadorDTO.class);
        BancoContadorDTO bancoContadorDTO1 = new BancoContadorDTO();
        bancoContadorDTO1.setId(1L);
        BancoContadorDTO bancoContadorDTO2 = new BancoContadorDTO();
        assertThat(bancoContadorDTO1).isNotEqualTo(bancoContadorDTO2);
        bancoContadorDTO2.setId(bancoContadorDTO1.getId());
        assertThat(bancoContadorDTO1).isEqualTo(bancoContadorDTO2);
        bancoContadorDTO2.setId(2L);
        assertThat(bancoContadorDTO1).isNotEqualTo(bancoContadorDTO2);
        bancoContadorDTO1.setId(null);
        assertThat(bancoContadorDTO1).isNotEqualTo(bancoContadorDTO2);
    }
}
