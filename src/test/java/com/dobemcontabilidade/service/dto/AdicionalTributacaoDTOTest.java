package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalTributacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalTributacaoDTO.class);
        AdicionalTributacaoDTO adicionalTributacaoDTO1 = new AdicionalTributacaoDTO();
        adicionalTributacaoDTO1.setId(1L);
        AdicionalTributacaoDTO adicionalTributacaoDTO2 = new AdicionalTributacaoDTO();
        assertThat(adicionalTributacaoDTO1).isNotEqualTo(adicionalTributacaoDTO2);
        adicionalTributacaoDTO2.setId(adicionalTributacaoDTO1.getId());
        assertThat(adicionalTributacaoDTO1).isEqualTo(adicionalTributacaoDTO2);
        adicionalTributacaoDTO2.setId(2L);
        assertThat(adicionalTributacaoDTO1).isNotEqualTo(adicionalTributacaoDTO2);
        adicionalTributacaoDTO1.setId(null);
        assertThat(adicionalTributacaoDTO1).isNotEqualTo(adicionalTributacaoDTO2);
    }
}
