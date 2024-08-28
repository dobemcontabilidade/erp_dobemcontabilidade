package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoAssinaturaContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalEnquadramentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalEnquadramento.class);
        AdicionalEnquadramento adicionalEnquadramento1 = getAdicionalEnquadramentoSample1();
        AdicionalEnquadramento adicionalEnquadramento2 = new AdicionalEnquadramento();
        assertThat(adicionalEnquadramento1).isNotEqualTo(adicionalEnquadramento2);

        adicionalEnquadramento2.setId(adicionalEnquadramento1.getId());
        assertThat(adicionalEnquadramento1).isEqualTo(adicionalEnquadramento2);

        adicionalEnquadramento2 = getAdicionalEnquadramentoSample2();
        assertThat(adicionalEnquadramento1).isNotEqualTo(adicionalEnquadramento2);
    }

    @Test
    void enquadramentoTest() {
        AdicionalEnquadramento adicionalEnquadramento = getAdicionalEnquadramentoRandomSampleGenerator();
        Enquadramento enquadramentoBack = getEnquadramentoRandomSampleGenerator();

        adicionalEnquadramento.setEnquadramento(enquadramentoBack);
        assertThat(adicionalEnquadramento.getEnquadramento()).isEqualTo(enquadramentoBack);

        adicionalEnquadramento.enquadramento(null);
        assertThat(adicionalEnquadramento.getEnquadramento()).isNull();
    }

    @Test
    void planoAssinaturaContabilTest() {
        AdicionalEnquadramento adicionalEnquadramento = getAdicionalEnquadramentoRandomSampleGenerator();
        PlanoAssinaturaContabil planoAssinaturaContabilBack = getPlanoAssinaturaContabilRandomSampleGenerator();

        adicionalEnquadramento.setPlanoAssinaturaContabil(planoAssinaturaContabilBack);
        assertThat(adicionalEnquadramento.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabilBack);

        adicionalEnquadramento.planoAssinaturaContabil(null);
        assertThat(adicionalEnquadramento.getPlanoAssinaturaContabil()).isNull();
    }
}
