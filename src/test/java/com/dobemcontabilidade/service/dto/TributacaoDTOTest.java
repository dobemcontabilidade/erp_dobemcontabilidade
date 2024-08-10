package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TributacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TributacaoDTO.class);
        TributacaoDTO tributacaoDTO1 = new TributacaoDTO();
        tributacaoDTO1.setId(1L);
        TributacaoDTO tributacaoDTO2 = new TributacaoDTO();
        assertThat(tributacaoDTO1).isNotEqualTo(tributacaoDTO2);
        tributacaoDTO2.setId(tributacaoDTO1.getId());
        assertThat(tributacaoDTO1).isEqualTo(tributacaoDTO2);
        tributacaoDTO2.setId(2L);
        assertThat(tributacaoDTO1).isNotEqualTo(tributacaoDTO2);
        tributacaoDTO1.setId(null);
        assertThat(tributacaoDTO1).isNotEqualTo(tributacaoDTO2);
    }
}
