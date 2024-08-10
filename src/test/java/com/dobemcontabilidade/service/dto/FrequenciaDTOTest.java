package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrequenciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FrequenciaDTO.class);
        FrequenciaDTO frequenciaDTO1 = new FrequenciaDTO();
        frequenciaDTO1.setId(1L);
        FrequenciaDTO frequenciaDTO2 = new FrequenciaDTO();
        assertThat(frequenciaDTO1).isNotEqualTo(frequenciaDTO2);
        frequenciaDTO2.setId(frequenciaDTO1.getId());
        assertThat(frequenciaDTO1).isEqualTo(frequenciaDTO2);
        frequenciaDTO2.setId(2L);
        assertThat(frequenciaDTO1).isNotEqualTo(frequenciaDTO2);
        frequenciaDTO1.setId(null);
        assertThat(frequenciaDTO1).isNotEqualTo(frequenciaDTO2);
    }
}
