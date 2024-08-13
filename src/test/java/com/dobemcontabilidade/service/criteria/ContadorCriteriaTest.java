package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ContadorCriteriaTest {

    @Test
    void newContadorCriteriaHasAllFiltersNullTest() {
        var contadorCriteria = new ContadorCriteria();
        assertThat(contadorCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void contadorCriteriaFluentMethodsCreatesFiltersTest() {
        var contadorCriteria = new ContadorCriteria();

        setAllFilters(contadorCriteria);

        assertThat(contadorCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void contadorCriteriaCopyCreatesNullFilterTest() {
        var contadorCriteria = new ContadorCriteria();
        var copy = contadorCriteria.copy();

        assertThat(contadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(contadorCriteria)
        );
    }

    @Test
    void contadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var contadorCriteria = new ContadorCriteria();
        setAllFilters(contadorCriteria);

        var copy = contadorCriteria.copy();

        assertThat(contadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(contadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var contadorCriteria = new ContadorCriteria();

        assertThat(contadorCriteria).hasToString("ContadorCriteria{}");
    }

    private static void setAllFilters(ContadorCriteria contadorCriteria) {
        contadorCriteria.id();
        contadorCriteria.crc();
        contadorCriteria.limiteEmpresas();
        contadorCriteria.limiteDepartamentos();
        contadorCriteria.limiteFaturamento();
        contadorCriteria.situacaoContador();
        contadorCriteria.pessoaId();
        contadorCriteria.usuarioContadorId();
        contadorCriteria.areaContabilAssinaturaEmpresaId();
        contadorCriteria.feedBackContadorParaUsuarioId();
        contadorCriteria.ordemServicoId();
        contadorCriteria.areaContabilContadorId();
        contadorCriteria.contadorResponsavelOrdemServicoId();
        contadorCriteria.contadorResponsavelTarefaRecorrenteId();
        contadorCriteria.departamentoEmpresaId();
        contadorCriteria.departamentoContadorId();
        contadorCriteria.termoAdesaoContadorId();
        contadorCriteria.avaliacaoContadorId();
        contadorCriteria.tarefaEmpresaId();
        contadorCriteria.perfilContadorId();
        contadorCriteria.distinct();
    }

    private static Condition<ContadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCrc()) &&
                condition.apply(criteria.getLimiteEmpresas()) &&
                condition.apply(criteria.getLimiteDepartamentos()) &&
                condition.apply(criteria.getLimiteFaturamento()) &&
                condition.apply(criteria.getSituacaoContador()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getUsuarioContadorId()) &&
                condition.apply(criteria.getAreaContabilAssinaturaEmpresaId()) &&
                condition.apply(criteria.getFeedBackContadorParaUsuarioId()) &&
                condition.apply(criteria.getOrdemServicoId()) &&
                condition.apply(criteria.getAreaContabilContadorId()) &&
                condition.apply(criteria.getContadorResponsavelOrdemServicoId()) &&
                condition.apply(criteria.getContadorResponsavelTarefaRecorrenteId()) &&
                condition.apply(criteria.getDepartamentoEmpresaId()) &&
                condition.apply(criteria.getDepartamentoContadorId()) &&
                condition.apply(criteria.getTermoAdesaoContadorId()) &&
                condition.apply(criteria.getAvaliacaoContadorId()) &&
                condition.apply(criteria.getTarefaEmpresaId()) &&
                condition.apply(criteria.getPerfilContadorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ContadorCriteria> copyFiltersAre(ContadorCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCrc(), copy.getCrc()) &&
                condition.apply(criteria.getLimiteEmpresas(), copy.getLimiteEmpresas()) &&
                condition.apply(criteria.getLimiteDepartamentos(), copy.getLimiteDepartamentos()) &&
                condition.apply(criteria.getLimiteFaturamento(), copy.getLimiteFaturamento()) &&
                condition.apply(criteria.getSituacaoContador(), copy.getSituacaoContador()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getUsuarioContadorId(), copy.getUsuarioContadorId()) &&
                condition.apply(criteria.getAreaContabilAssinaturaEmpresaId(), copy.getAreaContabilAssinaturaEmpresaId()) &&
                condition.apply(criteria.getFeedBackContadorParaUsuarioId(), copy.getFeedBackContadorParaUsuarioId()) &&
                condition.apply(criteria.getOrdemServicoId(), copy.getOrdemServicoId()) &&
                condition.apply(criteria.getAreaContabilContadorId(), copy.getAreaContabilContadorId()) &&
                condition.apply(criteria.getContadorResponsavelOrdemServicoId(), copy.getContadorResponsavelOrdemServicoId()) &&
                condition.apply(criteria.getContadorResponsavelTarefaRecorrenteId(), copy.getContadorResponsavelTarefaRecorrenteId()) &&
                condition.apply(criteria.getDepartamentoEmpresaId(), copy.getDepartamentoEmpresaId()) &&
                condition.apply(criteria.getDepartamentoContadorId(), copy.getDepartamentoContadorId()) &&
                condition.apply(criteria.getTermoAdesaoContadorId(), copy.getTermoAdesaoContadorId()) &&
                condition.apply(criteria.getAvaliacaoContadorId(), copy.getAvaliacaoContadorId()) &&
                condition.apply(criteria.getTarefaEmpresaId(), copy.getTarefaEmpresaId()) &&
                condition.apply(criteria.getPerfilContadorId(), copy.getPerfilContadorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
