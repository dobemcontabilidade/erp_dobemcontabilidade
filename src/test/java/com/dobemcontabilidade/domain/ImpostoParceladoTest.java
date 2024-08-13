package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ImpostoAPagarEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoParceladoTestSamples.*;
import static com.dobemcontabilidade.domain.ParcelamentoImpostoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImpostoParceladoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpostoParcelado.class);
        ImpostoParcelado impostoParcelado1 = getImpostoParceladoSample1();
        ImpostoParcelado impostoParcelado2 = new ImpostoParcelado();
        assertThat(impostoParcelado1).isNotEqualTo(impostoParcelado2);

        impostoParcelado2.setId(impostoParcelado1.getId());
        assertThat(impostoParcelado1).isEqualTo(impostoParcelado2);

        impostoParcelado2 = getImpostoParceladoSample2();
        assertThat(impostoParcelado1).isNotEqualTo(impostoParcelado2);
    }

    @Test
    void parcelamentoImpostoTest() {
        ImpostoParcelado impostoParcelado = getImpostoParceladoRandomSampleGenerator();
        ParcelamentoImposto parcelamentoImpostoBack = getParcelamentoImpostoRandomSampleGenerator();

        impostoParcelado.setParcelamentoImposto(parcelamentoImpostoBack);
        assertThat(impostoParcelado.getParcelamentoImposto()).isEqualTo(parcelamentoImpostoBack);

        impostoParcelado.parcelamentoImposto(null);
        assertThat(impostoParcelado.getParcelamentoImposto()).isNull();
    }

    @Test
    void impostoAPagarEmpresaTest() {
        ImpostoParcelado impostoParcelado = getImpostoParceladoRandomSampleGenerator();
        ImpostoAPagarEmpresa impostoAPagarEmpresaBack = getImpostoAPagarEmpresaRandomSampleGenerator();

        impostoParcelado.setImpostoAPagarEmpresa(impostoAPagarEmpresaBack);
        assertThat(impostoParcelado.getImpostoAPagarEmpresa()).isEqualTo(impostoAPagarEmpresaBack);

        impostoParcelado.impostoAPagarEmpresa(null);
        assertThat(impostoParcelado.getImpostoAPagarEmpresa()).isNull();
    }
}
