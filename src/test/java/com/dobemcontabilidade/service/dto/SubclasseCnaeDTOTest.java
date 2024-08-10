package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubclasseCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubclasseCnaeDTO.class);
        SubclasseCnaeDTO subclasseCnaeDTO1 = new SubclasseCnaeDTO();
        subclasseCnaeDTO1.setId(1L);
        SubclasseCnaeDTO subclasseCnaeDTO2 = new SubclasseCnaeDTO();
        assertThat(subclasseCnaeDTO1).isNotEqualTo(subclasseCnaeDTO2);
        subclasseCnaeDTO2.setId(subclasseCnaeDTO1.getId());
        assertThat(subclasseCnaeDTO1).isEqualTo(subclasseCnaeDTO2);
        subclasseCnaeDTO2.setId(2L);
        assertThat(subclasseCnaeDTO1).isNotEqualTo(subclasseCnaeDTO2);
        subclasseCnaeDTO1.setId(null);
        assertThat(subclasseCnaeDTO1).isNotEqualTo(subclasseCnaeDTO2);
    }
}
