package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDenunciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDenunciaDTO.class);
        TipoDenunciaDTO tipoDenunciaDTO1 = new TipoDenunciaDTO();
        tipoDenunciaDTO1.setId(1L);
        TipoDenunciaDTO tipoDenunciaDTO2 = new TipoDenunciaDTO();
        assertThat(tipoDenunciaDTO1).isNotEqualTo(tipoDenunciaDTO2);
        tipoDenunciaDTO2.setId(tipoDenunciaDTO1.getId());
        assertThat(tipoDenunciaDTO1).isEqualTo(tipoDenunciaDTO2);
        tipoDenunciaDTO2.setId(2L);
        assertThat(tipoDenunciaDTO1).isNotEqualTo(tipoDenunciaDTO2);
        tipoDenunciaDTO1.setId(null);
        assertThat(tipoDenunciaDTO1).isNotEqualTo(tipoDenunciaDTO2);
    }
}
