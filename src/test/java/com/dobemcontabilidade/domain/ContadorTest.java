package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AvaliacaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.BancoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TermoAdesaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contador.class);
        Contador contador1 = getContadorSample1();
        Contador contador2 = new Contador();
        assertThat(contador1).isNotEqualTo(contador2);

        contador2.setId(contador1.getId());
        assertThat(contador1).isEqualTo(contador2);

        contador2 = getContadorSample2();
        assertThat(contador1).isNotEqualTo(contador2);
    }

    @Test
    void pessoaTest() {
        Contador contador = getContadorRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        contador.setPessoa(pessoaBack);
        assertThat(contador.getPessoa()).isEqualTo(pessoaBack);

        contador.pessoa(null);
        assertThat(contador.getPessoa()).isNull();
    }

    @Test
    void areaContabilEmpresaTest() {
        Contador contador = getContadorRandomSampleGenerator();
        AreaContabilEmpresa areaContabilEmpresaBack = getAreaContabilEmpresaRandomSampleGenerator();

        contador.addAreaContabilEmpresa(areaContabilEmpresaBack);
        assertThat(contador.getAreaContabilEmpresas()).containsOnly(areaContabilEmpresaBack);
        assertThat(areaContabilEmpresaBack.getContador()).isEqualTo(contador);

        contador.removeAreaContabilEmpresa(areaContabilEmpresaBack);
        assertThat(contador.getAreaContabilEmpresas()).doesNotContain(areaContabilEmpresaBack);
        assertThat(areaContabilEmpresaBack.getContador()).isNull();

        contador.areaContabilEmpresas(new HashSet<>(Set.of(areaContabilEmpresaBack)));
        assertThat(contador.getAreaContabilEmpresas()).containsOnly(areaContabilEmpresaBack);
        assertThat(areaContabilEmpresaBack.getContador()).isEqualTo(contador);

        contador.setAreaContabilEmpresas(new HashSet<>());
        assertThat(contador.getAreaContabilEmpresas()).doesNotContain(areaContabilEmpresaBack);
        assertThat(areaContabilEmpresaBack.getContador()).isNull();
    }

    @Test
    void areaContabilContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        AreaContabilContador areaContabilContadorBack = getAreaContabilContadorRandomSampleGenerator();

        contador.addAreaContabilContador(areaContabilContadorBack);
        assertThat(contador.getAreaContabilContadors()).containsOnly(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getContador()).isEqualTo(contador);

        contador.removeAreaContabilContador(areaContabilContadorBack);
        assertThat(contador.getAreaContabilContadors()).doesNotContain(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getContador()).isNull();

        contador.areaContabilContadors(new HashSet<>(Set.of(areaContabilContadorBack)));
        assertThat(contador.getAreaContabilContadors()).containsOnly(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getContador()).isEqualTo(contador);

        contador.setAreaContabilContadors(new HashSet<>());
        assertThat(contador.getAreaContabilContadors()).doesNotContain(areaContabilContadorBack);
        assertThat(areaContabilContadorBack.getContador()).isNull();
    }

    @Test
    void departamentoEmpresaTest() {
        Contador contador = getContadorRandomSampleGenerator();
        DepartamentoEmpresa departamentoEmpresaBack = getDepartamentoEmpresaRandomSampleGenerator();

        contador.addDepartamentoEmpresa(departamentoEmpresaBack);
        assertThat(contador.getDepartamentoEmpresas()).containsOnly(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getContador()).isEqualTo(contador);

        contador.removeDepartamentoEmpresa(departamentoEmpresaBack);
        assertThat(contador.getDepartamentoEmpresas()).doesNotContain(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getContador()).isNull();

        contador.departamentoEmpresas(new HashSet<>(Set.of(departamentoEmpresaBack)));
        assertThat(contador.getDepartamentoEmpresas()).containsOnly(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getContador()).isEqualTo(contador);

        contador.setDepartamentoEmpresas(new HashSet<>());
        assertThat(contador.getDepartamentoEmpresas()).doesNotContain(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getContador()).isNull();
    }

    @Test
    void departamentoContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        DepartamentoContador departamentoContadorBack = getDepartamentoContadorRandomSampleGenerator();

        contador.addDepartamentoContador(departamentoContadorBack);
        assertThat(contador.getDepartamentoContadors()).containsOnly(departamentoContadorBack);
        assertThat(departamentoContadorBack.getContador()).isEqualTo(contador);

        contador.removeDepartamentoContador(departamentoContadorBack);
        assertThat(contador.getDepartamentoContadors()).doesNotContain(departamentoContadorBack);
        assertThat(departamentoContadorBack.getContador()).isNull();

        contador.departamentoContadors(new HashSet<>(Set.of(departamentoContadorBack)));
        assertThat(contador.getDepartamentoContadors()).containsOnly(departamentoContadorBack);
        assertThat(departamentoContadorBack.getContador()).isEqualTo(contador);

        contador.setDepartamentoContadors(new HashSet<>());
        assertThat(contador.getDepartamentoContadors()).doesNotContain(departamentoContadorBack);
        assertThat(departamentoContadorBack.getContador()).isNull();
    }

    @Test
    void termoAdesaoContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        TermoAdesaoContador termoAdesaoContadorBack = getTermoAdesaoContadorRandomSampleGenerator();

        contador.addTermoAdesaoContador(termoAdesaoContadorBack);
        assertThat(contador.getTermoAdesaoContadors()).containsOnly(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getContador()).isEqualTo(contador);

        contador.removeTermoAdesaoContador(termoAdesaoContadorBack);
        assertThat(contador.getTermoAdesaoContadors()).doesNotContain(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getContador()).isNull();

        contador.termoAdesaoContadors(new HashSet<>(Set.of(termoAdesaoContadorBack)));
        assertThat(contador.getTermoAdesaoContadors()).containsOnly(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getContador()).isEqualTo(contador);

        contador.setTermoAdesaoContadors(new HashSet<>());
        assertThat(contador.getTermoAdesaoContadors()).doesNotContain(termoAdesaoContadorBack);
        assertThat(termoAdesaoContadorBack.getContador()).isNull();
    }

    @Test
    void bancoContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        BancoContador bancoContadorBack = getBancoContadorRandomSampleGenerator();

        contador.addBancoContador(bancoContadorBack);
        assertThat(contador.getBancoContadors()).containsOnly(bancoContadorBack);
        assertThat(bancoContadorBack.getContador()).isEqualTo(contador);

        contador.removeBancoContador(bancoContadorBack);
        assertThat(contador.getBancoContadors()).doesNotContain(bancoContadorBack);
        assertThat(bancoContadorBack.getContador()).isNull();

        contador.bancoContadors(new HashSet<>(Set.of(bancoContadorBack)));
        assertThat(contador.getBancoContadors()).containsOnly(bancoContadorBack);
        assertThat(bancoContadorBack.getContador()).isEqualTo(contador);

        contador.setBancoContadors(new HashSet<>());
        assertThat(contador.getBancoContadors()).doesNotContain(bancoContadorBack);
        assertThat(bancoContadorBack.getContador()).isNull();
    }

    @Test
    void avaliacaoContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        AvaliacaoContador avaliacaoContadorBack = getAvaliacaoContadorRandomSampleGenerator();

        contador.addAvaliacaoContador(avaliacaoContadorBack);
        assertThat(contador.getAvaliacaoContadors()).containsOnly(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getContador()).isEqualTo(contador);

        contador.removeAvaliacaoContador(avaliacaoContadorBack);
        assertThat(contador.getAvaliacaoContadors()).doesNotContain(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getContador()).isNull();

        contador.avaliacaoContadors(new HashSet<>(Set.of(avaliacaoContadorBack)));
        assertThat(contador.getAvaliacaoContadors()).containsOnly(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getContador()).isEqualTo(contador);

        contador.setAvaliacaoContadors(new HashSet<>());
        assertThat(contador.getAvaliacaoContadors()).doesNotContain(avaliacaoContadorBack);
        assertThat(avaliacaoContadorBack.getContador()).isNull();
    }

    @Test
    void tarefaEmpresaTest() {
        Contador contador = getContadorRandomSampleGenerator();
        TarefaEmpresa tarefaEmpresaBack = getTarefaEmpresaRandomSampleGenerator();

        contador.addTarefaEmpresa(tarefaEmpresaBack);
        assertThat(contador.getTarefaEmpresas()).containsOnly(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getContador()).isEqualTo(contador);

        contador.removeTarefaEmpresa(tarefaEmpresaBack);
        assertThat(contador.getTarefaEmpresas()).doesNotContain(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getContador()).isNull();

        contador.tarefaEmpresas(new HashSet<>(Set.of(tarefaEmpresaBack)));
        assertThat(contador.getTarefaEmpresas()).containsOnly(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getContador()).isEqualTo(contador);

        contador.setTarefaEmpresas(new HashSet<>());
        assertThat(contador.getTarefaEmpresas()).doesNotContain(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getContador()).isNull();
    }

    @Test
    void perfilContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        PerfilContador perfilContadorBack = getPerfilContadorRandomSampleGenerator();

        contador.setPerfilContador(perfilContadorBack);
        assertThat(contador.getPerfilContador()).isEqualTo(perfilContadorBack);

        contador.perfilContador(null);
        assertThat(contador.getPerfilContador()).isNull();
    }

    @Test
    void usuarioContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        UsuarioContador usuarioContadorBack = getUsuarioContadorRandomSampleGenerator();

        contador.setUsuarioContador(usuarioContadorBack);
        assertThat(contador.getUsuarioContador()).isEqualTo(usuarioContadorBack);
        assertThat(usuarioContadorBack.getContador()).isEqualTo(contador);

        contador.usuarioContador(null);
        assertThat(contador.getUsuarioContador()).isNull();
        assertThat(usuarioContadorBack.getContador()).isNull();
    }
}
