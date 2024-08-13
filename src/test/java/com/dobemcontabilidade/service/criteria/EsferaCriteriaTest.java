package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EsferaCriteriaTest {

    @Test
    void newEsferaCriteriaHasAllFiltersNullTest() {
        var esferaCriteria = new EsferaCriteria();
        assertThat(esferaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void esferaCriteriaFluentMethodsCreatesFiltersTest() {
        var esferaCriteria = new EsferaCriteria();

        setAllFilters(esferaCriteria);

        assertThat(esferaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void esferaCriteriaCopyCreatesNullFilterTest() {
        var esferaCriteria = new EsferaCriteria();
        var copy = esferaCriteria.copy();

        assertThat(esferaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(esferaCriteria)
        );
    }

    @Test
    void esferaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var esferaCriteria = new EsferaCriteria();
        setAllFilters(esferaCriteria);

        var copy = esferaCriteria.copy();

        assertThat(esferaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(esferaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var esferaCriteria = new EsferaCriteria();

        assertThat(esferaCriteria).hasToString("EsferaCriteria{}");
    }

    private static void setAllFilters(EsferaCriteria esferaCriteria) {
        esferaCriteria.id();
        esferaCriteria.nome();
        esferaCriteria.servicoContabilId();
        esferaCriteria.impostoId();
        esferaCriteria.tarefaId();
        esferaCriteria.distinct();
    }

    private static Condition<EsferaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getServicoContabilId()) &&
                condition.apply(criteria.getImpostoId()) &&
                condition.apply(criteria.getTarefaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EsferaCriteria> copyFiltersAre(EsferaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getServicoContabilId(), copy.getServicoContabilId()) &&
                condition.apply(criteria.getImpostoId(), copy.getImpostoId()) &&
                condition.apply(criteria.getTarefaId(), copy.getTarefaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
