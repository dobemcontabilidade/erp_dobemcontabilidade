package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DenunciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DenunciaDTO.class);
        DenunciaDTO denunciaDTO1 = new DenunciaDTO();
        denunciaDTO1.setId(1L);
        DenunciaDTO denunciaDTO2 = new DenunciaDTO();
        assertThat(denunciaDTO1).isNotEqualTo(denunciaDTO2);
        denunciaDTO2.setId(denunciaDTO1.getId());
        assertThat(denunciaDTO1).isEqualTo(denunciaDTO2);
        denunciaDTO2.setId(2L);
        assertThat(denunciaDTO1).isNotEqualTo(denunciaDTO2);
        denunciaDTO1.setId(null);
        assertThat(denunciaDTO1).isNotEqualTo(denunciaDTO2);
    }
}
