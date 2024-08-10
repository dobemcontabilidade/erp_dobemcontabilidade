package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DescontoPlanoContabilDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoPlanoContabilDTO.class);
        DescontoPlanoContabilDTO descontoPlanoContabilDTO1 = new DescontoPlanoContabilDTO();
        descontoPlanoContabilDTO1.setId(1L);
        DescontoPlanoContabilDTO descontoPlanoContabilDTO2 = new DescontoPlanoContabilDTO();
        assertThat(descontoPlanoContabilDTO1).isNotEqualTo(descontoPlanoContabilDTO2);
        descontoPlanoContabilDTO2.setId(descontoPlanoContabilDTO1.getId());
        assertThat(descontoPlanoContabilDTO1).isEqualTo(descontoPlanoContabilDTO2);
        descontoPlanoContabilDTO2.setId(2L);
        assertThat(descontoPlanoContabilDTO1).isNotEqualTo(descontoPlanoContabilDTO2);
        descontoPlanoContabilDTO1.setId(null);
        assertThat(descontoPlanoContabilDTO1).isNotEqualTo(descontoPlanoContabilDTO2);
    }
}
