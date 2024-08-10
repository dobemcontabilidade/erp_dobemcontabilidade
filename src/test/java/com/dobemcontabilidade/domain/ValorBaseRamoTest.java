package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.ValorBaseRamoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValorBaseRamoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValorBaseRamo.class);
        ValorBaseRamo valorBaseRamo1 = getValorBaseRamoSample1();
        ValorBaseRamo valorBaseRamo2 = new ValorBaseRamo();
        assertThat(valorBaseRamo1).isNotEqualTo(valorBaseRamo2);

        valorBaseRamo2.setId(valorBaseRamo1.getId());
        assertThat(valorBaseRamo1).isEqualTo(valorBaseRamo2);

        valorBaseRamo2 = getValorBaseRamoSample2();
        assertThat(valorBaseRamo1).isNotEqualTo(valorBaseRamo2);
    }

    @Test
    void ramoTest() {
        ValorBaseRamo valorBaseRamo = getValorBaseRamoRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        valorBaseRamo.setRamo(ramoBack);
        assertThat(valorBaseRamo.getRamo()).isEqualTo(ramoBack);

        valorBaseRamo.ramo(null);
        assertThat(valorBaseRamo.getRamo()).isNull();
    }

    @Test
    void planoContabilTest() {
        ValorBaseRamo valorBaseRamo = getValorBaseRamoRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        valorBaseRamo.setPlanoContabil(planoContabilBack);
        assertThat(valorBaseRamo.getPlanoContabil()).isEqualTo(planoContabilBack);

        valorBaseRamo.planoContabil(null);
        assertThat(valorBaseRamo.getPlanoContabil()).isNull();
    }
}
