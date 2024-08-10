package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoEmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoEmpresaDTO.class);
        EnderecoEmpresaDTO enderecoEmpresaDTO1 = new EnderecoEmpresaDTO();
        enderecoEmpresaDTO1.setId(1L);
        EnderecoEmpresaDTO enderecoEmpresaDTO2 = new EnderecoEmpresaDTO();
        assertThat(enderecoEmpresaDTO1).isNotEqualTo(enderecoEmpresaDTO2);
        enderecoEmpresaDTO2.setId(enderecoEmpresaDTO1.getId());
        assertThat(enderecoEmpresaDTO1).isEqualTo(enderecoEmpresaDTO2);
        enderecoEmpresaDTO2.setId(2L);
        assertThat(enderecoEmpresaDTO1).isNotEqualTo(enderecoEmpresaDTO2);
        enderecoEmpresaDTO1.setId(null);
        assertThat(enderecoEmpresaDTO1).isNotEqualTo(enderecoEmpresaDTO2);
    }
}
