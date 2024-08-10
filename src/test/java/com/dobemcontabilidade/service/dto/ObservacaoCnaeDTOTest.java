package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObservacaoCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservacaoCnaeDTO.class);
        ObservacaoCnaeDTO observacaoCnaeDTO1 = new ObservacaoCnaeDTO();
        observacaoCnaeDTO1.setId(1L);
        ObservacaoCnaeDTO observacaoCnaeDTO2 = new ObservacaoCnaeDTO();
        assertThat(observacaoCnaeDTO1).isNotEqualTo(observacaoCnaeDTO2);
        observacaoCnaeDTO2.setId(observacaoCnaeDTO1.getId());
        assertThat(observacaoCnaeDTO1).isEqualTo(observacaoCnaeDTO2);
        observacaoCnaeDTO2.setId(2L);
        assertThat(observacaoCnaeDTO1).isNotEqualTo(observacaoCnaeDTO2);
        observacaoCnaeDTO1.setId(null);
        assertThat(observacaoCnaeDTO1).isNotEqualTo(observacaoCnaeDTO2);
    }
}
