package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EsferaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsferaDTO.class);
        EsferaDTO esferaDTO1 = new EsferaDTO();
        esferaDTO1.setId(1L);
        EsferaDTO esferaDTO2 = new EsferaDTO();
        assertThat(esferaDTO1).isNotEqualTo(esferaDTO2);
        esferaDTO2.setId(esferaDTO1.getId());
        assertThat(esferaDTO1).isEqualTo(esferaDTO2);
        esferaDTO2.setId(2L);
        assertThat(esferaDTO1).isNotEqualTo(esferaDTO2);
        esferaDTO1.setId(null);
        assertThat(esferaDTO1).isNotEqualTo(esferaDTO2);
    }
}
