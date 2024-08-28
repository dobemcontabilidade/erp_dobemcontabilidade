package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.EstadoTestSamples.*;
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
