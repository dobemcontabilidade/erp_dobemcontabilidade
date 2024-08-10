package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasseCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseCnaeDTO.class);
        ClasseCnaeDTO classeCnaeDTO1 = new ClasseCnaeDTO();
        classeCnaeDTO1.setId(1L);
        ClasseCnaeDTO classeCnaeDTO2 = new ClasseCnaeDTO();
        assertThat(classeCnaeDTO1).isNotEqualTo(classeCnaeDTO2);
        classeCnaeDTO2.setId(classeCnaeDTO1.getId());
        assertThat(classeCnaeDTO1).isEqualTo(classeCnaeDTO2);
        classeCnaeDTO2.setId(2L);
        assertThat(classeCnaeDTO1).isNotEqualTo(classeCnaeDTO2);
        classeCnaeDTO1.setId(null);
        assertThat(classeCnaeDTO1).isNotEqualTo(classeCnaeDTO2);
    }
}
