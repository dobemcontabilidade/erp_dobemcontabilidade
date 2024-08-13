package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ImpostoAPagarEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoParceladoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ImpostoAPagarEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpostoAPagarEmpresa.class);
        ImpostoAPagarEmpresa impostoAPagarEmpresa1 = getImpostoAPagarEmpresaSample1();
        ImpostoAPagarEmpresa impostoAPagarEmpresa2 = new ImpostoAPagarEmpresa();
        assertThat(impostoAPagarEmpresa1).isNotEqualTo(impostoAPagarEmpresa2);

        impostoAPagarEmpresa2.setId(impostoAPagarEmpresa1.getId());
        assertThat(impostoAPagarEmpresa1).isEqualTo(impostoAPagarEmpresa2);

        impostoAPagarEmpresa2 = getImpostoAPagarEmpresaSample2();
        assertThat(impostoAPagarEmpresa1).isNotEqualTo(impostoAPagarEmpresa2);
    }

    @Test
    void impostoParceladoTest() {
        ImpostoAPagarEmpresa impostoAPagarEmpresa = getImpostoAPagarEmpresaRandomSampleGenerator();
        ImpostoParcelado impostoParceladoBack = getImpostoParceladoRandomSampleGenerator();

        impostoAPagarEmpresa.addImpostoParcelado(impostoParceladoBack);
        assertThat(impostoAPagarEmpresa.getImpostoParcelados()).containsOnly(impostoParceladoBack);
        assertThat(impostoParceladoBack.getImpostoAPagarEmpresa()).isEqualTo(impostoAPagarEmpresa);

        impostoAPagarEmpresa.removeImpostoParcelado(impostoParceladoBack);
        assertThat(impostoAPagarEmpresa.getImpostoParcelados()).doesNotContain(impostoParceladoBack);
        assertThat(impostoParceladoBack.getImpostoAPagarEmpresa()).isNull();

        impostoAPagarEmpresa.impostoParcelados(new HashSet<>(Set.of(impostoParceladoBack)));
        assertThat(impostoAPagarEmpresa.getImpostoParcelados()).containsOnly(impostoParceladoBack);
        assertThat(impostoParceladoBack.getImpostoAPagarEmpresa()).isEqualTo(impostoAPagarEmpresa);

        impostoAPagarEmpresa.setImpostoParcelados(new HashSet<>());
        assertThat(impostoAPagarEmpresa.getImpostoParcelados()).doesNotContain(impostoParceladoBack);
        assertThat(impostoParceladoBack.getImpostoAPagarEmpresa()).isNull();
    }

    @Test
    void impostoTest() {
        ImpostoAPagarEmpresa impostoAPagarEmpresa = getImpostoAPagarEmpresaRandomSampleGenerator();
        ImpostoEmpresa impostoEmpresaBack = getImpostoEmpresaRandomSampleGenerator();

        impostoAPagarEmpresa.setImposto(impostoEmpresaBack);
        assertThat(impostoAPagarEmpresa.getImposto()).isEqualTo(impostoEmpresaBack);

        impostoAPagarEmpresa.imposto(null);
        assertThat(impostoAPagarEmpresa.getImposto()).isNull();
    }
}
