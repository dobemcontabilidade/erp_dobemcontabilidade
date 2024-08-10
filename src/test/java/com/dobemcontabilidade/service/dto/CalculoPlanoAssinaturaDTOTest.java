package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalculoPlanoAssinaturaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalculoPlanoAssinaturaDTO.class);
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO1 = new CalculoPlanoAssinaturaDTO();
        calculoPlanoAssinaturaDTO1.setId(1L);
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO2 = new CalculoPlanoAssinaturaDTO();
        assertThat(calculoPlanoAssinaturaDTO1).isNotEqualTo(calculoPlanoAssinaturaDTO2);
        calculoPlanoAssinaturaDTO2.setId(calculoPlanoAssinaturaDTO1.getId());
        assertThat(calculoPlanoAssinaturaDTO1).isEqualTo(calculoPlanoAssinaturaDTO2);
        calculoPlanoAssinaturaDTO2.setId(2L);
        assertThat(calculoPlanoAssinaturaDTO1).isNotEqualTo(calculoPlanoAssinaturaDTO2);
        calculoPlanoAssinaturaDTO1.setId(null);
        assertThat(calculoPlanoAssinaturaDTO1).isNotEqualTo(calculoPlanoAssinaturaDTO2);
    }
}
