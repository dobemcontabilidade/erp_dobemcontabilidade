package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PrazoAssinaturaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrazoAssinaturaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrazoAssinatura.class);
        PrazoAssinatura prazoAssinatura1 = getPrazoAssinaturaSample1();
        PrazoAssinatura prazoAssinatura2 = new PrazoAssinatura();
        assertThat(prazoAssinatura1).isNotEqualTo(prazoAssinatura2);

        prazoAssinatura2.setId(prazoAssinatura1.getId());
        assertThat(prazoAssinatura1).isEqualTo(prazoAssinatura2);

        prazoAssinatura2 = getPrazoAssinaturaSample2();
        assertThat(prazoAssinatura1).isNotEqualTo(prazoAssinatura2);
    }
}
