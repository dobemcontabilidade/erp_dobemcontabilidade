package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EtapaFluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.FluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModeloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EtapaFluxoModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaFluxoModelo.class);
        EtapaFluxoModelo etapaFluxoModelo1 = getEtapaFluxoModeloSample1();
        EtapaFluxoModelo etapaFluxoModelo2 = new EtapaFluxoModelo();
        assertThat(etapaFluxoModelo1).isNotEqualTo(etapaFluxoModelo2);

        etapaFluxoModelo2.setId(etapaFluxoModelo1.getId());
        assertThat(etapaFluxoModelo1).isEqualTo(etapaFluxoModelo2);

        etapaFluxoModelo2 = getEtapaFluxoModeloSample2();
        assertThat(etapaFluxoModelo1).isNotEqualTo(etapaFluxoModelo2);
    }

    @Test
    void servicoContabilEtapaFluxoModeloTest() {
        EtapaFluxoModelo etapaFluxoModelo = getEtapaFluxoModeloRandomSampleGenerator();
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModeloBack = getServicoContabilEtapaFluxoModeloRandomSampleGenerator();

        etapaFluxoModelo.addServicoContabilEtapaFluxoModelo(servicoContabilEtapaFluxoModeloBack);
        assertThat(etapaFluxoModelo.getServicoContabilEtapaFluxoModelos()).containsOnly(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getEtapaFluxoModelo()).isEqualTo(etapaFluxoModelo);

        etapaFluxoModelo.removeServicoContabilEtapaFluxoModelo(servicoContabilEtapaFluxoModeloBack);
        assertThat(etapaFluxoModelo.getServicoContabilEtapaFluxoModelos()).doesNotContain(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getEtapaFluxoModelo()).isNull();

        etapaFluxoModelo.servicoContabilEtapaFluxoModelos(new HashSet<>(Set.of(servicoContabilEtapaFluxoModeloBack)));
        assertThat(etapaFluxoModelo.getServicoContabilEtapaFluxoModelos()).containsOnly(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getEtapaFluxoModelo()).isEqualTo(etapaFluxoModelo);

        etapaFluxoModelo.setServicoContabilEtapaFluxoModelos(new HashSet<>());
        assertThat(etapaFluxoModelo.getServicoContabilEtapaFluxoModelos()).doesNotContain(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getEtapaFluxoModelo()).isNull();
    }

    @Test
    void fluxoModeloTest() {
        EtapaFluxoModelo etapaFluxoModelo = getEtapaFluxoModeloRandomSampleGenerator();
        FluxoModelo fluxoModeloBack = getFluxoModeloRandomSampleGenerator();

        etapaFluxoModelo.setFluxoModelo(fluxoModeloBack);
        assertThat(etapaFluxoModelo.getFluxoModelo()).isEqualTo(fluxoModeloBack);

        etapaFluxoModelo.fluxoModelo(null);
        assertThat(etapaFluxoModelo.getFluxoModelo()).isNull();
    }
}
