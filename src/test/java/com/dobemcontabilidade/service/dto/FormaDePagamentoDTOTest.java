package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormaDePagamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaDePagamentoDTO.class);
        FormaDePagamentoDTO formaDePagamentoDTO1 = new FormaDePagamentoDTO();
        formaDePagamentoDTO1.setId(1L);
        FormaDePagamentoDTO formaDePagamentoDTO2 = new FormaDePagamentoDTO();
        assertThat(formaDePagamentoDTO1).isNotEqualTo(formaDePagamentoDTO2);
        formaDePagamentoDTO2.setId(formaDePagamentoDTO1.getId());
        assertThat(formaDePagamentoDTO1).isEqualTo(formaDePagamentoDTO2);
        formaDePagamentoDTO2.setId(2L);
        assertThat(formaDePagamentoDTO1).isNotEqualTo(formaDePagamentoDTO2);
        formaDePagamentoDTO1.setId(null);
        assertThat(formaDePagamentoDTO1).isNotEqualTo(formaDePagamentoDTO2);
    }
}
