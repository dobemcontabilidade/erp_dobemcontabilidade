package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AreaContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabil.class);
        AreaContabil areaContabil1 = getAreaContabilSample1();
        AreaContabil areaContabil2 = new AreaContabil();
        assertThat(areaContabil1).isNotEqualTo(areaContabil2);

        areaContabil2.setId(areaContabil1.getId());
        assertThat(areaContabil1).isEqualTo(areaContabil2);

        areaContabil2 = getAreaContabilSample2();
        assertThat(areaContabil1).isNotEqualTo(areaContabil2);
    }

    @Test
    void areaContabilAssinaturaEmpresaTest() {
        AreaContabil areaContabil = getAreaContabilRandomSampleGenerator();
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresaBack = getAreaContabilAssinaturaEmpresaRandomSampleGenerator();

        areaContabil.addAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabil.getAreaContabilAssinaturaEmpresas()).containsOnly(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.removeAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabil.getAreaContabilAssinaturaEmpresas()).doesNotContain(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAreaContabil()).isNull();

        areaContabil.areaContabilAssinaturaEmpresas(new HashSet<>(Set.of(areaContabilAssinaturaEmpresaBack)));
        assertThat(areaContabil.getAreaContabilAssinaturaEmpresas()).containsOnly(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.setAreaContabilAssinaturaEmpresas(new HashSet<>());
        assertThat(areaContabil.getAreaContabilAssinaturaEmpresas()).doesNotContain(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAreaContabil()).isNull();
    }

    @Test
    void servicoContabilTest() {
        AreaContabil areaContabil = getAreaContabilRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        areaContabil.addServicoContabil(servicoContabilBack);
        assertThat(areaContabil.getServicoContabils()).containsOnly(servicoContabilBack);
        assertThat(servicoContabilBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.removeServicoContabil(servicoContabilBack);
        assertThat(areaContabil.getServicoContabils()).doesNotContain(servicoContabilBack);
        assertThat(servicoContabilBack.getAreaContabil()).isNull();

        areaContabil.servicoContabils(new HashSet<>(Set.of(servicoContabilBack)));
        assertThat(areaContabil.getServicoContabils()).containsOnly(servicoContabilBack);
        assertThat(servicoContabilBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.setServicoContabils(new HashSet<>());
        assertThat(areaContabil.getServicoContabils()).doesNotContain(servicoContabilBack);
        assertThat(servicoContabilBack.getAreaContabil()).isNull();
    }

    @Test
    void areaContabilContadorTest() {
        AreaContabil areaContabil = getAreaContabilRandomSampleGenerator();
        AreaContabilContador areaContabilContadorBack = getAreaContabilContadorRandomSampleGenerator();

        areaContabil.addAreaContabilContador(areaContabilContadorBack);
        assertThat(areaContabil.getAreaContabilContadors()).containsOnly(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.removeAreaContabilContador(areaContabilContadorBack);
        assertThat(areaContabil.getAreaContabilContadors()).doesNotContain(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isNull();

        areaContabil.areaContabilContadors(new HashSet<>(Set.of(areaContabilContadorBack)));
        assertThat(areaContabil.getAreaContabilContadors()).containsOnly(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isEqualTo(areaContabil);

        areaContabil.setAreaContabilContadors(new HashSet<>());
        assertThat(areaContabil.getAreaContabilContadors()).doesNotContain(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getAreaContabil()).isNull();
    }
}
