package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalRamoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoAssinaturaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalRamoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalRamo.class);
        AdicionalRamo adicionalRamo1 = getAdicionalRamoSample1();
        AdicionalRamo adicionalRamo2 = new AdicionalRamo();
        assertThat(adicionalRamo1).isNotEqualTo(adicionalRamo2);

        adicionalRamo2.setId(adicionalRamo1.getId());
        assertThat(adicionalRamo1).isEqualTo(adicionalRamo2);

        adicionalRamo2 = getAdicionalRamoSample2();
        assertThat(adicionalRamo1).isNotEqualTo(adicionalRamo2);
    }

    @Test
    void ramoTest() {
        AdicionalRamo adicionalRamo = getAdicionalRamoRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        adicionalRamo.setRamo(ramoBack);
        assertThat(adicionalRamo.getRamo()).isEqualTo(ramoBack);

        adicionalRamo.ramo(null);
        assertThat(adicionalRamo.getRamo()).isNull();
    }

    @Test
    void planoAssinaturaContabilTest() {
        AdicionalRamo adicionalRamo = getAdicionalRamoRandomSampleGenerator();
        PlanoAssinaturaContabil planoAssinaturaContabilBack = getPlanoAssinaturaContabilRandomSampleGenerator();

        adicionalRamo.setPlanoAssinaturaContabil(planoAssinaturaContabilBack);
        assertThat(adicionalRamo.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabilBack);

        adicionalRamo.planoAssinaturaContabil(null);
        assertThat(adicionalRamo.getPlanoAssinaturaContabil()).isNull();
    }
}
