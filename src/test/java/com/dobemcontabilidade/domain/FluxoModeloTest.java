package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EtapaFluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.FluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FluxoModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoModelo.class);
        FluxoModelo fluxoModelo1 = getFluxoModeloSample1();
        FluxoModelo fluxoModelo2 = new FluxoModelo();
        assertThat(fluxoModelo1).isNotEqualTo(fluxoModelo2);

        fluxoModelo2.setId(fluxoModelo1.getId());
        assertThat(fluxoModelo1).isEqualTo(fluxoModelo2);

        fluxoModelo2 = getFluxoModeloSample2();
        assertThat(fluxoModelo1).isNotEqualTo(fluxoModelo2);
    }

    @Test
    void ordemServicoTest() {
        FluxoModelo fluxoModelo = getFluxoModeloRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        fluxoModelo.addOrdemServico(ordemServicoBack);
        assertThat(fluxoModelo.getOrdemServicos()).containsOnly(ordemServicoBack);
        assertThat(ordemServicoBack.getFluxoModelo()).isEqualTo(fluxoModelo);

        fluxoModelo.removeOrdemServico(ordemServicoBack);
        assertThat(fluxoModelo.getOrdemServicos()).doesNotContain(ordemServicoBack);
        assertThat(ordemServicoBack.getFluxoModelo()).isNull();

        fluxoModelo.ordemServicos(new HashSet<>(Set.of(ordemServicoBack)));
        assertThat(fluxoModelo.getOrdemServicos()).containsOnly(ordemServicoBack);
        assertThat(ordemServicoBack.getFluxoModelo()).isEqualTo(fluxoModelo);

        fluxoModelo.setOrdemServicos(new HashSet<>());
        assertThat(fluxoModelo.getOrdemServicos()).doesNotContain(ordemServicoBack);
        assertThat(ordemServicoBack.getFluxoModelo()).isNull();
    }

    @Test
    void etapaFluxoModeloTest() {
        FluxoModelo fluxoModelo = getFluxoModeloRandomSampleGenerator();
        EtapaFluxoModelo etapaFluxoModeloBack = getEtapaFluxoModeloRandomSampleGenerator();

        fluxoModelo.addEtapaFluxoModelo(etapaFluxoModeloBack);
        assertThat(fluxoModelo.getEtapaFluxoModelos()).containsOnly(etapaFluxoModeloBack);
        assertThat(etapaFluxoModeloBack.getFluxoModelo()).isEqualTo(fluxoModelo);

        fluxoModelo.removeEtapaFluxoModelo(etapaFluxoModeloBack);
        assertThat(fluxoModelo.getEtapaFluxoModelos()).doesNotContain(etapaFluxoModeloBack);
        assertThat(etapaFluxoModeloBack.getFluxoModelo()).isNull();

        fluxoModelo.etapaFluxoModelos(new HashSet<>(Set.of(etapaFluxoModeloBack)));
        assertThat(fluxoModelo.getEtapaFluxoModelos()).containsOnly(etapaFluxoModeloBack);
        assertThat(etapaFluxoModeloBack.getFluxoModelo()).isEqualTo(fluxoModelo);

        fluxoModelo.setEtapaFluxoModelos(new HashSet<>());
        assertThat(fluxoModelo.getEtapaFluxoModelos()).doesNotContain(etapaFluxoModeloBack);
        assertThat(etapaFluxoModeloBack.getFluxoModelo()).isNull();
    }

    @Test
    void cidadeTest() {
        FluxoModelo fluxoModelo = getFluxoModeloRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        fluxoModelo.setCidade(cidadeBack);
        assertThat(fluxoModelo.getCidade()).isEqualTo(cidadeBack);

        fluxoModelo.cidade(null);
        assertThat(fluxoModelo.getCidade()).isNull();
    }
}
