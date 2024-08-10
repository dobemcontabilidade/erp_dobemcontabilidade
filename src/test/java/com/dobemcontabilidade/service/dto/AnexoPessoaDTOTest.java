package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoPessoaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoPessoaDTO.class);
        AnexoPessoaDTO anexoPessoaDTO1 = new AnexoPessoaDTO();
        anexoPessoaDTO1.setId(1L);
        AnexoPessoaDTO anexoPessoaDTO2 = new AnexoPessoaDTO();
        assertThat(anexoPessoaDTO1).isNotEqualTo(anexoPessoaDTO2);
        anexoPessoaDTO2.setId(anexoPessoaDTO1.getId());
        assertThat(anexoPessoaDTO1).isEqualTo(anexoPessoaDTO2);
        anexoPessoaDTO2.setId(2L);
        assertThat(anexoPessoaDTO1).isNotEqualTo(anexoPessoaDTO2);
        anexoPessoaDTO1.setId(null);
        assertThat(anexoPessoaDTO1).isNotEqualTo(anexoPessoaDTO2);
    }
}
