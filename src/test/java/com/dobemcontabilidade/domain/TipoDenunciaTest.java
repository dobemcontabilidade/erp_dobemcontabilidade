package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.TipoDenunciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDenunciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDenuncia.class);
        TipoDenuncia tipoDenuncia1 = getTipoDenunciaSample1();
        TipoDenuncia tipoDenuncia2 = new TipoDenuncia();
        assertThat(tipoDenuncia1).isNotEqualTo(tipoDenuncia2);

        tipoDenuncia2.setId(tipoDenuncia1.getId());
        assertThat(tipoDenuncia1).isEqualTo(tipoDenuncia2);

        tipoDenuncia2 = getTipoDenunciaSample2();
        assertThat(tipoDenuncia1).isNotEqualTo(tipoDenuncia2);
    }
}
