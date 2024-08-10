package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoPagamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoPagamentoDTO.class);
        PeriodoPagamentoDTO periodoPagamentoDTO1 = new PeriodoPagamentoDTO();
        periodoPagamentoDTO1.setId(1L);
        PeriodoPagamentoDTO periodoPagamentoDTO2 = new PeriodoPagamentoDTO();
        assertThat(periodoPagamentoDTO1).isNotEqualTo(periodoPagamentoDTO2);
        periodoPagamentoDTO2.setId(periodoPagamentoDTO1.getId());
        assertThat(periodoPagamentoDTO1).isEqualTo(periodoPagamentoDTO2);
        periodoPagamentoDTO2.setId(2L);
        assertThat(periodoPagamentoDTO1).isNotEqualTo(periodoPagamentoDTO2);
        periodoPagamentoDTO1.setId(null);
        assertThat(periodoPagamentoDTO1).isNotEqualTo(periodoPagamentoDTO2);
    }
}
