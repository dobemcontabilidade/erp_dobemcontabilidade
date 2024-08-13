package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EtapaFluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicoContabilEtapaFluxoModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoContabilEtapaFluxoModelo.class);
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo1 = getServicoContabilEtapaFluxoModeloSample1();
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo2 = new ServicoContabilEtapaFluxoModelo();
        assertThat(servicoContabilEtapaFluxoModelo1).isNotEqualTo(servicoContabilEtapaFluxoModelo2);

        servicoContabilEtapaFluxoModelo2.setId(servicoContabilEtapaFluxoModelo1.getId());
        assertThat(servicoContabilEtapaFluxoModelo1).isEqualTo(servicoContabilEtapaFluxoModelo2);

        servicoContabilEtapaFluxoModelo2 = getServicoContabilEtapaFluxoModeloSample2();
        assertThat(servicoContabilEtapaFluxoModelo1).isNotEqualTo(servicoContabilEtapaFluxoModelo2);
    }

    @Test
    void etapaFluxoModeloTest() {
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo = getServicoContabilEtapaFluxoModeloRandomSampleGenerator();
        EtapaFluxoModelo etapaFluxoModeloBack = getEtapaFluxoModeloRandomSampleGenerator();

        servicoContabilEtapaFluxoModelo.setEtapaFluxoModelo(etapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModelo.getEtapaFluxoModelo()).isEqualTo(etapaFluxoModeloBack);

        servicoContabilEtapaFluxoModelo.etapaFluxoModelo(null);
        assertThat(servicoContabilEtapaFluxoModelo.getEtapaFluxoModelo()).isNull();
    }

    @Test
    void servicoContabilTest() {
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo = getServicoContabilEtapaFluxoModeloRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        servicoContabilEtapaFluxoModelo.setServicoContabil(servicoContabilBack);
        assertThat(servicoContabilEtapaFluxoModelo.getServicoContabil()).isEqualTo(servicoContabilBack);

        servicoContabilEtapaFluxoModelo.servicoContabil(null);
        assertThat(servicoContabilEtapaFluxoModelo.getServicoContabil()).isNull();
    }
}
