package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AnexoRequeridoCriteriaTest {

    @Test
    void newAnexoRequeridoCriteriaHasAllFiltersNullTest() {
        var anexoRequeridoCriteria = new AnexoRequeridoCriteria();
        assertThat(anexoRequeridoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void anexoRequeridoCriteriaFluentMethodsCreatesFiltersTest() {
        var anexoRequeridoCriteria = new AnexoRequeridoCriteria();

        setAllFilters(anexoRequeridoCriteria);

        assertThat(anexoRequeridoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void anexoRequeridoCriteriaCopyCreatesNullFilterTest() {
        var anexoRequeridoCriteria = new AnexoRequeridoCriteria();
        var copy = anexoRequeridoCriteria.copy();

        assertThat(anexoRequeridoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(anexoRequeridoCriteria)
        );
    }

    @Test
    void anexoRequeridoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var anexoRequeridoCriteria = new AnexoRequeridoCriteria();
        setAllFilters(anexoRequeridoCriteria);

        var copy = anexoRequeridoCriteria.copy();

        assertThat(anexoRequeridoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(anexoRequeridoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var anexoRequeridoCriteria = new AnexoRequeridoCriteria();

        assertThat(anexoRequeridoCriteria).hasToString("AnexoRequeridoCriteria{}");
    }

    private static void setAllFilters(AnexoRequeridoCriteria anexoRequeridoCriteria) {
        anexoRequeridoCriteria.id();
        anexoRequeridoCriteria.nome();
        anexoRequeridoCriteria.tipo();
        anexoRequeridoCriteria.anexoRequeridoPessoaId();
        anexoRequeridoCriteria.anexoRequeridoEmpresaId();
        anexoRequeridoCriteria.anexoRequeridoServicoContabilId();
        anexoRequeridoCriteria.anexoServicoContabilEmpresaId();
        anexoRequeridoCriteria.anexoRequeridoTarefaOrdemServicoId();
        anexoRequeridoCriteria.anexoRequeridoTarefaRecorrenteId();
        anexoRequeridoCriteria.distinct();
    }

    private static Condition<AnexoRequeridoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getTipo()) &&
                condition.apply(criteria.getAnexoRequeridoPessoaId()) &&
                condition.apply(criteria.getAnexoRequeridoEmpresaId()) &&
                condition.apply(criteria.getAnexoRequeridoServicoContabilId()) &&
                condition.apply(criteria.getAnexoServicoContabilEmpresaId()) &&
                condition.apply(criteria.getAnexoRequeridoTarefaOrdemServicoId()) &&
                condition.apply(criteria.getAnexoRequeridoTarefaRecorrenteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AnexoRequeridoCriteria> copyFiltersAre(
        AnexoRequeridoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getTipo(), copy.getTipo()) &&
                condition.apply(criteria.getAnexoRequeridoPessoaId(), copy.getAnexoRequeridoPessoaId()) &&
                condition.apply(criteria.getAnexoRequeridoEmpresaId(), copy.getAnexoRequeridoEmpresaId()) &&
                condition.apply(criteria.getAnexoRequeridoServicoContabilId(), copy.getAnexoRequeridoServicoContabilId()) &&
                condition.apply(criteria.getAnexoServicoContabilEmpresaId(), copy.getAnexoServicoContabilEmpresaId()) &&
                condition.apply(criteria.getAnexoRequeridoTarefaOrdemServicoId(), copy.getAnexoRequeridoTarefaOrdemServicoId()) &&
                condition.apply(criteria.getAnexoRequeridoTarefaRecorrenteId(), copy.getAnexoRequeridoTarefaRecorrenteId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
