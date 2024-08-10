package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrazoAssinaturaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrazoAssinaturaDTO.class);
        PrazoAssinaturaDTO prazoAssinaturaDTO1 = new PrazoAssinaturaDTO();
        prazoAssinaturaDTO1.setId(1L);
        PrazoAssinaturaDTO prazoAssinaturaDTO2 = new PrazoAssinaturaDTO();
        assertThat(prazoAssinaturaDTO1).isNotEqualTo(prazoAssinaturaDTO2);
        prazoAssinaturaDTO2.setId(prazoAssinaturaDTO1.getId());
        assertThat(prazoAssinaturaDTO1).isEqualTo(prazoAssinaturaDTO2);
        prazoAssinaturaDTO2.setId(2L);
        assertThat(prazoAssinaturaDTO1).isNotEqualTo(prazoAssinaturaDTO2);
        prazoAssinaturaDTO1.setId(null);
        assertThat(prazoAssinaturaDTO1).isNotEqualTo(prazoAssinaturaDTO2);
    }
}
