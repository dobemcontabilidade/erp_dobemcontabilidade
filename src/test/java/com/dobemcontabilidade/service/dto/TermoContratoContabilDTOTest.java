package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermoContratoContabilDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoContratoContabilDTO.class);
        TermoContratoContabilDTO termoContratoContabilDTO1 = new TermoContratoContabilDTO();
        termoContratoContabilDTO1.setId(1L);
        TermoContratoContabilDTO termoContratoContabilDTO2 = new TermoContratoContabilDTO();
        assertThat(termoContratoContabilDTO1).isNotEqualTo(termoContratoContabilDTO2);
        termoContratoContabilDTO2.setId(termoContratoContabilDTO1.getId());
        assertThat(termoContratoContabilDTO1).isEqualTo(termoContratoContabilDTO2);
        termoContratoContabilDTO2.setId(2L);
        assertThat(termoContratoContabilDTO1).isNotEqualTo(termoContratoContabilDTO2);
        termoContratoContabilDTO1.setId(null);
        assertThat(termoContratoContabilDTO1).isNotEqualTo(termoContratoContabilDTO2);
    }
}
