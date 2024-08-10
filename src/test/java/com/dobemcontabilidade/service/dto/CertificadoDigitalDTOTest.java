package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CertificadoDigitalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificadoDigitalDTO.class);
        CertificadoDigitalDTO certificadoDigitalDTO1 = new CertificadoDigitalDTO();
        certificadoDigitalDTO1.setId(1L);
        CertificadoDigitalDTO certificadoDigitalDTO2 = new CertificadoDigitalDTO();
        assertThat(certificadoDigitalDTO1).isNotEqualTo(certificadoDigitalDTO2);
        certificadoDigitalDTO2.setId(certificadoDigitalDTO1.getId());
        assertThat(certificadoDigitalDTO1).isEqualTo(certificadoDigitalDTO2);
        certificadoDigitalDTO2.setId(2L);
        assertThat(certificadoDigitalDTO1).isNotEqualTo(certificadoDigitalDTO2);
        certificadoDigitalDTO1.setId(null);
        assertThat(certificadoDigitalDTO1).isNotEqualTo(certificadoDigitalDTO2);
    }
}
