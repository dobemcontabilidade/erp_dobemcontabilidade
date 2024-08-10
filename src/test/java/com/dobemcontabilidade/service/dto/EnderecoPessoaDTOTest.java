package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoPessoaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoPessoaDTO.class);
        EnderecoPessoaDTO enderecoPessoaDTO1 = new EnderecoPessoaDTO();
        enderecoPessoaDTO1.setId(1L);
        EnderecoPessoaDTO enderecoPessoaDTO2 = new EnderecoPessoaDTO();
        assertThat(enderecoPessoaDTO1).isNotEqualTo(enderecoPessoaDTO2);
        enderecoPessoaDTO2.setId(enderecoPessoaDTO1.getId());
        assertThat(enderecoPessoaDTO1).isEqualTo(enderecoPessoaDTO2);
        enderecoPessoaDTO2.setId(2L);
        assertThat(enderecoPessoaDTO1).isNotEqualTo(enderecoPessoaDTO2);
        enderecoPessoaDTO1.setId(null);
        assertThat(enderecoPessoaDTO1).isNotEqualTo(enderecoPessoaDTO2);
    }
}
