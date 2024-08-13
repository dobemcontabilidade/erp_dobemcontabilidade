package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AvaliacaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorResponsavelOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackContadorParaUsuarioTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
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
    void usuarioContadorTest() {
        Contador contador = getContadorRandomSampleGenerator();
        UsuarioContador usuarioContadorBack = getUsuarioContadorRandomSampleGenerator();

        contador.setUsuarioContador(usuarioContadorBack);
        assertThat(contador.getUsuarioContador()).isEqualTo(usuarioContadorBack);

        contador.usuarioContador(null);
        assertThat(contador.getUsuarioContador()).isNull();
    }

    @Test
    void areaContabilAssinaturaEmpresaTest() {
        Contador contador = getContadorRandomSampleGenerator();
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresaBack = getAreaContabilAssinaturaEmpresaRandomSampleGenerator();

        contador.addAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresaBack);
        assertThat(contador.getAreaContabilAssinaturaEmpresas()).containsOnly(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getContador()).isEqualTo(contador);

        contador.removeAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresaBack);
        assertThat(contador.getAreaContabilAssinaturaEmpresas()).doesNotContain(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getContador()).isNull();

        contador.areaContabilAssinaturaEmpresas(new HashSet<>(Set.of(areaContabilAssinaturaEmpresaBack)));
        assertThat(contador.getAreaContabilAssinaturaEmpresas()).containsOnly(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getContador()).isEqualTo(contador);

        contador.setAreaContabilAssinaturaEmpresas(new HashSet<>());
        assertThat(contador.getAreaContabilAssinaturaEmpresas()).doesNotContain(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getContador()).isNull();
    }

    @Test
    void feedBackContadorParaUsuarioTest() {
        Contador contador = getContadorRandomSampleGenerator();
        FeedBackContadorParaUsuario feedBackContadorParaUsuarioBack = getFeedBackContadorParaUsuarioRandomSampleGenerator();

        contador.addFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(contador.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getContador()).isEqualTo(contador);

        contador.removeFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(contador.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getContador()).isNull();

        contador.feedBackContadorParaUsuarios(new HashSet<>(Set.of(feedBackContadorParaUsuarioBack)));
        assertThat(contador.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getContador()).isEqualTo(contador);

        contador.setFeedBackContadorParaUsuarios(new HashSet<>());
        assertThat(contador.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getContador()).isNull();
    }

    @Test
    void ordemServicoTest() {
        Contador contador = getContadorRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        contador.addOrdemServico(ordemServicoBack);
        assertThat(contador.getOrdemServicos()).containsOnly(ordemServicoBack);
        assertThat(ordemServicoBack.getContador()).isEqualTo(contador);

        contador.removeOrdemServico(ordemServicoBack);
        assertThat(contador.getOrdemServicos()).doesNotContain(ordemServicoBack);
        assertThat(ordemServicoBack.getContador()).isNull();

        contador.ordemServicos(new HashSet<>(Set.of(ordemServicoBack)));
        assertThat(contador.getOrdemServicos()).containsOnly(ordemServicoBack);
        assertThat(ordemServicoBack.getContador()).isEqualTo(contador);

        contador.setOrdemServicos(new HashSet<>());
        assertThat(contador.getOrdemServicos()).doesNotContain(ordemServicoBack);
        assertThat(ordemServicoBack.getContador()).isNull();
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
    void contadorResponsavelOrdemServicoTest() {
        Contador contador = getContadorRandomSampleGenerator();
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServicoBack = getContadorResponsavelOrdemServicoRandomSampleGenerator();

        contador.addContadorResponsavelOrdemServico(contadorResponsavelOrdemServicoBack);
        assertThat(contador.getContadorResponsavelOrdemServicos()).containsOnly(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getContador()).isEqualTo(contador);

        contador.removeContadorResponsavelOrdemServico(contadorResponsavelOrdemServicoBack);
        assertThat(contador.getContadorResponsavelOrdemServicos()).doesNotContain(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getContador()).isNull();

        contador.contadorResponsavelOrdemServicos(new HashSet<>(Set.of(contadorResponsavelOrdemServicoBack)));
        assertThat(contador.getContadorResponsavelOrdemServicos()).containsOnly(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getContador()).isEqualTo(contador);

        contador.setContadorResponsavelOrdemServicos(new HashSet<>());
        assertThat(contador.getContadorResponsavelOrdemServicos()).doesNotContain(contadorResponsavelOrdemServicoBack);
        assertThat(contadorResponsavelOrdemServicoBack.getContador()).isNull();
    }

    @Test
    void contadorResponsavelTarefaRecorrenteTest() {
        Contador contador = getContadorRandomSampleGenerator();
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrenteBack =
            getContadorResponsavelTarefaRecorrenteRandomSampleGenerator();

        contador.addContadorResponsavelTarefaRecorrente(contadorResponsavelTarefaRecorrenteBack);
        assertThat(contador.getContadorResponsavelTarefaRecorrentes()).containsOnly(contadorResponsavelTarefaRecorrenteBack);
        assertThat(contadorResponsavelTarefaRecorrenteBack.getContador()).isEqualTo(contador);

        contador.removeContadorResponsavelTarefaRecorrente(contadorResponsavelTarefaRecorrenteBack);
        assertThat(contador.getContadorResponsavelTarefaRecorrentes()).doesNotContain(contadorResponsavelTarefaRecorrenteBack);
        assertThat(contadorResponsavelTarefaRecorrenteBack.getContador()).isNull();

        contador.contadorResponsavelTarefaRecorrentes(new HashSet<>(Set.of(contadorResponsavelTarefaRecorrenteBack)));
        assertThat(contador.getContadorResponsavelTarefaRecorrentes()).containsOnly(contadorResponsavelTarefaRecorrenteBack);
        assertThat(contadorResponsavelTarefaRecorrenteBack.getContador()).isEqualTo(contador);

        contador.setContadorResponsavelTarefaRecorrentes(new HashSet<>());
        assertThat(contador.getContadorResponsavelTarefaRecorrentes()).doesNotContain(contadorResponsavelTarefaRecorrenteBack);
        assertThat(contadorResponsavelTarefaRecorrenteBack.getContador()).isNull();
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
}
