package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DenunciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DenunciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Denuncia.class);
        Denuncia denuncia1 = getDenunciaSample1();
        Denuncia denuncia2 = new Denuncia();
        assertThat(denuncia1).isNotEqualTo(denuncia2);

        denuncia2.setId(denuncia1.getId());
        assertThat(denuncia1).isEqualTo(denuncia2);

        denuncia2 = getDenunciaSample2();
        assertThat(denuncia1).isNotEqualTo(denuncia2);
    }
}
