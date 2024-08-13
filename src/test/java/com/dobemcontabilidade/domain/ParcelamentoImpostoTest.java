package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoParceladoTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoTestSamples.*;
import static com.dobemcontabilidade.domain.ParcelaImpostoAPagarTestSamples.*;
import static com.dobemcontabilidade.domain.ParcelamentoImpostoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ParcelamentoImpostoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcelamentoImposto.class);
        ParcelamentoImposto parcelamentoImposto1 = getParcelamentoImpostoSample1();
        ParcelamentoImposto parcelamentoImposto2 = new ParcelamentoImposto();
        assertThat(parcelamentoImposto1).isNotEqualTo(parcelamentoImposto2);

        parcelamentoImposto2.setId(parcelamentoImposto1.getId());
        assertThat(parcelamentoImposto1).isEqualTo(parcelamentoImposto2);

        parcelamentoImposto2 = getParcelamentoImpostoSample2();
        assertThat(parcelamentoImposto1).isNotEqualTo(parcelamentoImposto2);
    }

    @Test
    void parcelaImpostoAPagarTest() {
        ParcelamentoImposto parcelamentoImposto = getParcelamentoImpostoRandomSampleGenerator();
        ParcelaImpostoAPagar parcelaImpostoAPagarBack = getParcelaImpostoAPagarRandomSampleGenerator();

        parcelamentoImposto.addParcelaImpostoAPagar(parcelaImpostoAPagarBack);
        assertThat(parcelamentoImposto.getParcelaImpostoAPagars()).containsOnly(parcelaImpostoAPagarBack);
        assertThat(parcelaImpostoAPagarBack.getParcelamentoImposto()).isEqualTo(parcelamentoImposto);

        parcelamentoImposto.removeParcelaImpostoAPagar(parcelaImpostoAPagarBack);
        assertThat(parcelamentoImposto.getParcelaImpostoAPagars()).doesNotContain(parcelaImpostoAPagarBack);
        assertThat(parcelaImpostoAPagarBack.getParcelamentoImposto()).isNull();

        parcelamentoImposto.parcelaImpostoAPagars(new HashSet<>(Set.of(parcelaImpostoAPagarBack)));
        assertThat(parcelamentoImposto.getParcelaImpostoAPagars()).containsOnly(parcelaImpostoAPagarBack);
        assertThat(parcelaImpostoAPagarBack.getParcelamentoImposto()).isEqualTo(parcelamentoImposto);

        parcelamentoImposto.setParcelaImpostoAPagars(new HashSet<>());
        assertThat(parcelamentoImposto.getParcelaImpostoAPagars()).doesNotContain(parcelaImpostoAPagarBack);
        assertThat(parcelaImpostoAPagarBack.getParcelamentoImposto()).isNull();
    }

    @Test
    void impostoParceladoTest() {
        ParcelamentoImposto parcelamentoImposto = getParcelamentoImpostoRandomSampleGenerator();
        ImpostoParcelado impostoParceladoBack = getImpostoParceladoRandomSampleGenerator();

        parcelamentoImposto.addImpostoParcelado(impostoParceladoBack);
        assertThat(parcelamentoImposto.getImpostoParcelados()).containsOnly(impostoParceladoBack);
        assertThat(impostoParceladoBack.getParcelamentoImposto()).isEqualTo(parcelamentoImposto);

        parcelamentoImposto.removeImpostoParcelado(impostoParceladoBack);
        assertThat(parcelamentoImposto.getImpostoParcelados()).doesNotContain(impostoParceladoBack);
        assertThat(impostoParceladoBack.getParcelamentoImposto()).isNull();

        parcelamentoImposto.impostoParcelados(new HashSet<>(Set.of(impostoParceladoBack)));
        assertThat(parcelamentoImposto.getImpostoParcelados()).containsOnly(impostoParceladoBack);
        assertThat(impostoParceladoBack.getParcelamentoImposto()).isEqualTo(parcelamentoImposto);

        parcelamentoImposto.setImpostoParcelados(new HashSet<>());
        assertThat(parcelamentoImposto.getImpostoParcelados()).doesNotContain(impostoParceladoBack);
        assertThat(impostoParceladoBack.getParcelamentoImposto()).isNull();
    }

    @Test
    void impostoTest() {
        ParcelamentoImposto parcelamentoImposto = getParcelamentoImpostoRandomSampleGenerator();
        Imposto impostoBack = getImpostoRandomSampleGenerator();

        parcelamentoImposto.setImposto(impostoBack);
        assertThat(parcelamentoImposto.getImposto()).isEqualTo(impostoBack);

        parcelamentoImposto.imposto(null);
        assertThat(parcelamentoImposto.getImposto()).isNull();
    }

    @Test
    void empresaTest() {
        ParcelamentoImposto parcelamentoImposto = getParcelamentoImpostoRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        parcelamentoImposto.setEmpresa(empresaBack);
        assertThat(parcelamentoImposto.getEmpresa()).isEqualTo(empresaBack);

        parcelamentoImposto.empresa(null);
        assertThat(parcelamentoImposto.getEmpresa()).isNull();
    }
}
