package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProfissaoCriteriaTest {

    @Test
    void newProfissaoCriteriaHasAllFiltersNullTest() {
        var profissaoCriteria = new ProfissaoCriteria();
        assertThat(profissaoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void profissaoCriteriaFluentMethodsCreatesFiltersTest() {
        var profissaoCriteria = new ProfissaoCriteria();

        setAllFilters(profissaoCriteria);

        assertThat(profissaoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void profissaoCriteriaCopyCreatesNullFilterTest() {
        var profissaoCriteria = new ProfissaoCriteria();
        var copy = profissaoCriteria.copy();

        assertThat(profissaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(profissaoCriteria)
        );
    }

    @Test
    void profissaoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var profissaoCriteria = new ProfissaoCriteria();
        setAllFilters(profissaoCriteria);

        var copy = profissaoCriteria.copy();

        assertThat(profissaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(profissaoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var profissaoCriteria = new ProfissaoCriteria();

        assertThat(profissaoCriteria).hasToString("ProfissaoCriteria{}");
    }

    private static void setAllFilters(ProfissaoCriteria profissaoCriteria) {
        profissaoCriteria.id();
        profissaoCriteria.nome();
        profissaoCriteria.cbo();
        profissaoCriteria.categoria();
        profissaoCriteria.funcionarioId();
        profissaoCriteria.socioId();
        profissaoCriteria.distinct();
    }

    private static Condition<ProfissaoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getCbo()) &&
                condition.apply(criteria.getCategoria()) &&
                condition.apply(criteria.getFuncionarioId()) &&
                condition.apply(criteria.getSocioId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProfissaoCriteria> copyFiltersAre(ProfissaoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getCbo(), copy.getCbo()) &&
                condition.apply(criteria.getCategoria(), copy.getCategoria()) &&
                condition.apply(criteria.getFuncionarioId(), copy.getFuncionarioId()) &&
                condition.apply(criteria.getSocioId(), copy.getSocioId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
