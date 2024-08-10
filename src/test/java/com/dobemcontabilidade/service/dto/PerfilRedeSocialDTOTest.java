package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilRedeSocialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilRedeSocialDTO.class);
        PerfilRedeSocialDTO perfilRedeSocialDTO1 = new PerfilRedeSocialDTO();
        perfilRedeSocialDTO1.setId(1L);
        PerfilRedeSocialDTO perfilRedeSocialDTO2 = new PerfilRedeSocialDTO();
        assertThat(perfilRedeSocialDTO1).isNotEqualTo(perfilRedeSocialDTO2);
        perfilRedeSocialDTO2.setId(perfilRedeSocialDTO1.getId());
        assertThat(perfilRedeSocialDTO1).isEqualTo(perfilRedeSocialDTO2);
        perfilRedeSocialDTO2.setId(2L);
        assertThat(perfilRedeSocialDTO1).isNotEqualTo(perfilRedeSocialDTO2);
        perfilRedeSocialDTO1.setId(null);
        assertThat(perfilRedeSocialDTO1).isNotEqualTo(perfilRedeSocialDTO2);
    }
}
