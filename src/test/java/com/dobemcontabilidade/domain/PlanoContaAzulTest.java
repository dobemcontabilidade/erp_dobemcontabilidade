package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContaAzulTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContaAzulTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanoContaAzulTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoContaAzul.class);
        PlanoContaAzul planoContaAzul1 = getPlanoContaAzulSample1();
        PlanoContaAzul planoContaAzul2 = new PlanoContaAzul();
        assertThat(planoContaAzul1).isNotEqualTo(planoContaAzul2);

        planoContaAzul2.setId(planoContaAzul1.getId());
        assertThat(planoContaAzul1).isEqualTo(planoContaAzul2);

        planoContaAzul2 = getPlanoContaAzulSample2();
        assertThat(planoContaAzul1).isNotEqualTo(planoContaAzul2);
    }

    @Test
    void calculoPlanoAssinaturaTest() {
        PlanoContaAzul planoContaAzul = getPlanoContaAzulRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        planoContaAzul.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(planoContaAzul.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContaAzul()).isEqualTo(planoContaAzul);

        planoContaAzul.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(planoContaAzul.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContaAzul()).isNull();

        planoContaAzul.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(planoContaAzul.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContaAzul()).isEqualTo(planoContaAzul);

        planoContaAzul.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(planoContaAzul.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContaAzul()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        PlanoContaAzul planoContaAzul = getPlanoContaAzulRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        planoContaAzul.addAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(planoContaAzul.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContaAzul()).isEqualTo(planoContaAzul);

        planoContaAzul.removeAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(planoContaAzul.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContaAzul()).isNull();

        planoContaAzul.assinaturaEmpresas(new HashSet<>(Set.of(assinaturaEmpresaBack)));
        assertThat(planoContaAzul.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContaAzul()).isEqualTo(planoContaAzul);

        planoContaAzul.setAssinaturaEmpresas(new HashSet<>());
        assertThat(planoContaAzul.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContaAzul()).isNull();
    }

    @Test
    void descontoPlanoContaAzulTest() {
        PlanoContaAzul planoContaAzul = getPlanoContaAzulRandomSampleGenerator();
        DescontoPlanoContaAzul descontoPlanoContaAzulBack = getDescontoPlanoContaAzulRandomSampleGenerator();

        planoContaAzul.addDescontoPlanoContaAzul(descontoPlanoContaAzulBack);
        assertThat(planoContaAzul.getDescontoPlanoContaAzuls()).containsOnly(descontoPlanoContaAzulBack);
        assertThat(descontoPlanoContaAzulBack.getPlanoContaAzul()).isEqualTo(planoContaAzul);

        planoContaAzul.removeDescontoPlanoContaAzul(descontoPlanoContaAzulBack);
        assertThat(planoContaAzul.getDescontoPlanoContaAzuls()).doesNotContain(descontoPlanoContaAzulBack);
        assertThat(descontoPlanoContaAzulBack.getPlanoContaAzul()).isNull();

        planoContaAzul.descontoPlanoContaAzuls(new HashSet<>(Set.of(descontoPlanoContaAzulBack)));
        assertThat(planoContaAzul.getDescontoPlanoContaAzuls()).containsOnly(descontoPlanoContaAzulBack);
        assertThat(descontoPlanoContaAzulBack.getPlanoContaAzul()).isEqualTo(planoContaAzul);

        planoContaAzul.setDescontoPlanoContaAzuls(new HashSet<>());
        assertThat(planoContaAzul.getDescontoPlanoContaAzuls()).doesNotContain(descontoPlanoContaAzulBack);
        assertThat(descontoPlanoContaAzulBack.getPlanoContaAzul()).isNull();
    }
}
