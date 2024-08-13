package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ParcelaImpostoAPagarTestSamples.*;
import static com.dobemcontabilidade.domain.ParcelamentoImpostoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParcelaImpostoAPagarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcelaImpostoAPagar.class);
        ParcelaImpostoAPagar parcelaImpostoAPagar1 = getParcelaImpostoAPagarSample1();
        ParcelaImpostoAPagar parcelaImpostoAPagar2 = new ParcelaImpostoAPagar();
        assertThat(parcelaImpostoAPagar1).isNotEqualTo(parcelaImpostoAPagar2);

        parcelaImpostoAPagar2.setId(parcelaImpostoAPagar1.getId());
        assertThat(parcelaImpostoAPagar1).isEqualTo(parcelaImpostoAPagar2);

        parcelaImpostoAPagar2 = getParcelaImpostoAPagarSample2();
        assertThat(parcelaImpostoAPagar1).isNotEqualTo(parcelaImpostoAPagar2);
    }

    @Test
    void parcelamentoImpostoTest() {
        ParcelaImpostoAPagar parcelaImpostoAPagar = getParcelaImpostoAPagarRandomSampleGenerator();
        ParcelamentoImposto parcelamentoImpostoBack = getParcelamentoImpostoRandomSampleGenerator();

        parcelaImpostoAPagar.setParcelamentoImposto(parcelamentoImpostoBack);
        assertThat(parcelaImpostoAPagar.getParcelamentoImposto()).isEqualTo(parcelamentoImpostoBack);

        parcelaImpostoAPagar.parcelamentoImposto(null);
        assertThat(parcelaImpostoAPagar.getParcelamentoImposto()).isNull();
    }
}
