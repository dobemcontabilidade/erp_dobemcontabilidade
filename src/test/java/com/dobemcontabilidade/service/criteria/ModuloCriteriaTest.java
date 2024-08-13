package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ModuloCriteriaTest {

    @Test
    void newModuloCriteriaHasAllFiltersNullTest() {
        var moduloCriteria = new ModuloCriteria();
        assertThat(moduloCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void moduloCriteriaFluentMethodsCreatesFiltersTest() {
        var moduloCriteria = new ModuloCriteria();

        setAllFilters(moduloCriteria);

        assertThat(moduloCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void moduloCriteriaCopyCreatesNullFilterTest() {
        var moduloCriteria = new ModuloCriteria();
        var copy = moduloCriteria.copy();

        assertThat(moduloCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(moduloCriteria)
        );
    }

    @Test
    void moduloCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var moduloCriteria = new ModuloCriteria();
        setAllFilters(moduloCriteria);

        var copy = moduloCriteria.copy();

        assertThat(moduloCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(moduloCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var moduloCriteria = new ModuloCriteria();

        assertThat(moduloCriteria).hasToString("ModuloCriteria{}");
    }

    private static void setAllFilters(ModuloCriteria moduloCriteria) {
        moduloCriteria.id();
        moduloCriteria.nome();
        moduloCriteria.descricao();
        moduloCriteria.funcionalidadeId();
        moduloCriteria.sistemaId();
        moduloCriteria.distinct();
    }

    private static Condition<ModuloCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getDescricao()) &&
                condition.apply(criteria.getFuncionalidadeId()) &&
                condition.apply(criteria.getSistemaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ModuloCriteria> copyFiltersAre(ModuloCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getDescricao(), copy.getDescricao()) &&
                condition.apply(criteria.getFuncionalidadeId(), copy.getFuncionalidadeId()) &&
                condition.apply(criteria.getSistemaId(), copy.getSistemaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
