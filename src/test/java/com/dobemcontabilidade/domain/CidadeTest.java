package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgenteIntegracaoEstagioTestSamples.*;
import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.EstadoTestSamples.*;
import static com.dobemcontabilidade.domain.FluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.InstituicaoEnsinoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidade.class);
        Cidade cidade1 = getCidadeSample1();
        Cidade cidade2 = new Cidade();
        assertThat(cidade1).isNotEqualTo(cidade2);

        cidade2.setId(cidade1.getId());
        assertThat(cidade1).isEqualTo(cidade2);

        cidade2 = getCidadeSample2();
        assertThat(cidade1).isNotEqualTo(cidade2);
    }

    @Test
    void instituicaoEnsinoTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        InstituicaoEnsino instituicaoEnsinoBack = getInstituicaoEnsinoRandomSampleGenerator();

        cidade.addInstituicaoEnsino(instituicaoEnsinoBack);
        assertThat(cidade.getInstituicaoEnsinos()).containsOnly(instituicaoEnsinoBack);
        assertThat(instituicaoEnsinoBack.getCidade()).isEqualTo(cidade);

        cidade.removeInstituicaoEnsino(instituicaoEnsinoBack);
        assertThat(cidade.getInstituicaoEnsinos()).doesNotContain(instituicaoEnsinoBack);
        assertThat(instituicaoEnsinoBack.getCidade()).isNull();

        cidade.instituicaoEnsinos(new HashSet<>(Set.of(instituicaoEnsinoBack)));
        assertThat(cidade.getInstituicaoEnsinos()).containsOnly(instituicaoEnsinoBack);
        assertThat(instituicaoEnsinoBack.getCidade()).isEqualTo(cidade);

        cidade.setInstituicaoEnsinos(new HashSet<>());
        assertThat(cidade.getInstituicaoEnsinos()).doesNotContain(instituicaoEnsinoBack);
        assertThat(instituicaoEnsinoBack.getCidade()).isNull();
    }

    @Test
    void agenteIntegracaoEstagioTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        AgenteIntegracaoEstagio agenteIntegracaoEstagioBack = getAgenteIntegracaoEstagioRandomSampleGenerator();

        cidade.addAgenteIntegracaoEstagio(agenteIntegracaoEstagioBack);
        assertThat(cidade.getAgenteIntegracaoEstagios()).containsOnly(agenteIntegracaoEstagioBack);
        assertThat(agenteIntegracaoEstagioBack.getCidade()).isEqualTo(cidade);

        cidade.removeAgenteIntegracaoEstagio(agenteIntegracaoEstagioBack);
        assertThat(cidade.getAgenteIntegracaoEstagios()).doesNotContain(agenteIntegracaoEstagioBack);
        assertThat(agenteIntegracaoEstagioBack.getCidade()).isNull();

        cidade.agenteIntegracaoEstagios(new HashSet<>(Set.of(agenteIntegracaoEstagioBack)));
        assertThat(cidade.getAgenteIntegracaoEstagios()).containsOnly(agenteIntegracaoEstagioBack);
        assertThat(agenteIntegracaoEstagioBack.getCidade()).isEqualTo(cidade);

        cidade.setAgenteIntegracaoEstagios(new HashSet<>());
        assertThat(cidade.getAgenteIntegracaoEstagios()).doesNotContain(agenteIntegracaoEstagioBack);
        assertThat(agenteIntegracaoEstagioBack.getCidade()).isNull();
    }

    @Test
    void empresaModeloTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        cidade.addEmpresaModelo(empresaModeloBack);
        assertThat(cidade.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getCidade()).isEqualTo(cidade);

        cidade.removeEmpresaModelo(empresaModeloBack);
        assertThat(cidade.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getCidade()).isNull();

        cidade.empresaModelos(new HashSet<>(Set.of(empresaModeloBack)));
        assertThat(cidade.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getCidade()).isEqualTo(cidade);

        cidade.setEmpresaModelos(new HashSet<>());
        assertThat(cidade.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getCidade()).isNull();
    }

    @Test
    void fluxoModeloTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        FluxoModelo fluxoModeloBack = getFluxoModeloRandomSampleGenerator();

        cidade.addFluxoModelo(fluxoModeloBack);
        assertThat(cidade.getFluxoModelos()).containsOnly(fluxoModeloBack);
        assertThat(fluxoModeloBack.getCidade()).isEqualTo(cidade);

        cidade.removeFluxoModelo(fluxoModeloBack);
        assertThat(cidade.getFluxoModelos()).doesNotContain(fluxoModeloBack);
        assertThat(fluxoModeloBack.getCidade()).isNull();

        cidade.fluxoModelos(new HashSet<>(Set.of(fluxoModeloBack)));
        assertThat(cidade.getFluxoModelos()).containsOnly(fluxoModeloBack);
        assertThat(fluxoModeloBack.getCidade()).isEqualTo(cidade);

        cidade.setFluxoModelos(new HashSet<>());
        assertThat(cidade.getFluxoModelos()).doesNotContain(fluxoModeloBack);
        assertThat(fluxoModeloBack.getCidade()).isNull();
    }

    @Test
    void enderecoPessoaTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        EnderecoPessoa enderecoPessoaBack = getEnderecoPessoaRandomSampleGenerator();

        cidade.addEnderecoPessoa(enderecoPessoaBack);
        assertThat(cidade.getEnderecoPessoas()).containsOnly(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getCidade()).isEqualTo(cidade);

        cidade.removeEnderecoPessoa(enderecoPessoaBack);
        assertThat(cidade.getEnderecoPessoas()).doesNotContain(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getCidade()).isNull();

        cidade.enderecoPessoas(new HashSet<>(Set.of(enderecoPessoaBack)));
        assertThat(cidade.getEnderecoPessoas()).containsOnly(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getCidade()).isEqualTo(cidade);

        cidade.setEnderecoPessoas(new HashSet<>());
        assertThat(cidade.getEnderecoPessoas()).doesNotContain(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getCidade()).isNull();
    }

    @Test
    void enderecoEmpresaTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        EnderecoEmpresa enderecoEmpresaBack = getEnderecoEmpresaRandomSampleGenerator();

        cidade.addEnderecoEmpresa(enderecoEmpresaBack);
        assertThat(cidade.getEnderecoEmpresas()).containsOnly(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getCidade()).isEqualTo(cidade);

        cidade.removeEnderecoEmpresa(enderecoEmpresaBack);
        assertThat(cidade.getEnderecoEmpresas()).doesNotContain(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getCidade()).isNull();

        cidade.enderecoEmpresas(new HashSet<>(Set.of(enderecoEmpresaBack)));
        assertThat(cidade.getEnderecoEmpresas()).containsOnly(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getCidade()).isEqualTo(cidade);

        cidade.setEnderecoEmpresas(new HashSet<>());
        assertThat(cidade.getEnderecoEmpresas()).doesNotContain(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getCidade()).isNull();
    }

    @Test
    void estadoTest() {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Estado estadoBack = getEstadoRandomSampleGenerator();

        cidade.setEstado(estadoBack);
        assertThat(cidade.getEstado()).isEqualTo(estadoBack);

        cidade.estado(null);
        assertThat(cidade.getEstado()).isNull();
    }
}
