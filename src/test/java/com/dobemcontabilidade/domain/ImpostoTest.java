package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EsferaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoTestSamples.*;
import static com.dobemcontabilidade.domain.ParcelamentoImpostoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ImpostoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imposto.class);
        Imposto imposto1 = getImpostoSample1();
        Imposto imposto2 = new Imposto();
        assertThat(imposto1).isNotEqualTo(imposto2);

        imposto2.setId(imposto1.getId());
        assertThat(imposto1).isEqualTo(imposto2);

        imposto2 = getImpostoSample2();
        assertThat(imposto1).isNotEqualTo(imposto2);
    }

    @Test
    void impostoEmpresaTest() {
        Imposto imposto = getImpostoRandomSampleGenerator();
        ImpostoEmpresa impostoEmpresaBack = getImpostoEmpresaRandomSampleGenerator();

        imposto.addImpostoEmpresa(impostoEmpresaBack);
        assertThat(imposto.getImpostoEmpresas()).containsOnly(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getImposto()).isEqualTo(imposto);

        imposto.removeImpostoEmpresa(impostoEmpresaBack);
        assertThat(imposto.getImpostoEmpresas()).doesNotContain(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getImposto()).isNull();

        imposto.impostoEmpresas(new HashSet<>(Set.of(impostoEmpresaBack)));
        assertThat(imposto.getImpostoEmpresas()).containsOnly(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getImposto()).isEqualTo(imposto);

        imposto.setImpostoEmpresas(new HashSet<>());
        assertThat(imposto.getImpostoEmpresas()).doesNotContain(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getImposto()).isNull();
    }

    @Test
    void parcelamentoImpostoTest() {
        Imposto imposto = getImpostoRandomSampleGenerator();
        ParcelamentoImposto parcelamentoImpostoBack = getParcelamentoImpostoRandomSampleGenerator();

        imposto.addParcelamentoImposto(parcelamentoImpostoBack);
        assertThat(imposto.getParcelamentoImpostos()).containsOnly(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getImposto()).isEqualTo(imposto);

        imposto.removeParcelamentoImposto(parcelamentoImpostoBack);
        assertThat(imposto.getParcelamentoImpostos()).doesNotContain(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getImposto()).isNull();

        imposto.parcelamentoImpostos(new HashSet<>(Set.of(parcelamentoImpostoBack)));
        assertThat(imposto.getParcelamentoImpostos()).containsOnly(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getImposto()).isEqualTo(imposto);

        imposto.setParcelamentoImpostos(new HashSet<>());
        assertThat(imposto.getParcelamentoImpostos()).doesNotContain(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getImposto()).isNull();
    }

    @Test
    void impostoEmpresaModeloTest() {
        Imposto imposto = getImpostoRandomSampleGenerator();
        ImpostoEmpresaModelo impostoEmpresaModeloBack = getImpostoEmpresaModeloRandomSampleGenerator();

        imposto.addImpostoEmpresaModelo(impostoEmpresaModeloBack);
        assertThat(imposto.getImpostoEmpresaModelos()).containsOnly(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getImposto()).isEqualTo(imposto);

        imposto.removeImpostoEmpresaModelo(impostoEmpresaModeloBack);
        assertThat(imposto.getImpostoEmpresaModelos()).doesNotContain(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getImposto()).isNull();

        imposto.impostoEmpresaModelos(new HashSet<>(Set.of(impostoEmpresaModeloBack)));
        assertThat(imposto.getImpostoEmpresaModelos()).containsOnly(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getImposto()).isEqualTo(imposto);

        imposto.setImpostoEmpresaModelos(new HashSet<>());
        assertThat(imposto.getImpostoEmpresaModelos()).doesNotContain(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getImposto()).isNull();
    }

    @Test
    void esferaTest() {
        Imposto imposto = getImpostoRandomSampleGenerator();
        Esfera esferaBack = getEsferaRandomSampleGenerator();

        imposto.setEsfera(esferaBack);
        assertThat(imposto.getEsfera()).isEqualTo(esferaBack);

        imposto.esfera(null);
        assertThat(imposto.getEsfera()).isNull();
    }
}
