package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class RamoCriteriaTest {

    @Test
    void newRamoCriteriaHasAllFiltersNullTest() {
        var ramoCriteria = new RamoCriteria();
        assertThat(ramoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ramoCriteriaFluentMethodsCreatesFiltersTest() {
        var ramoCriteria = new RamoCriteria();

        setAllFilters(ramoCriteria);

        assertThat(ramoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ramoCriteriaCopyCreatesNullFilterTest() {
        var ramoCriteria = new RamoCriteria();
        var copy = ramoCriteria.copy();

        assertThat(ramoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ramoCriteria)
        );
    }

    @Test
    void ramoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ramoCriteria = new RamoCriteria();
        setAllFilters(ramoCriteria);

        var copy = ramoCriteria.copy();

        assertThat(ramoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ramoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ramoCriteria = new RamoCriteria();

        assertThat(ramoCriteria).hasToString("RamoCriteria{}");
    }

    private static void setAllFilters(RamoCriteria ramoCriteria) {
        ramoCriteria.id();
        ramoCriteria.nome();
        ramoCriteria.empresaId();
        ramoCriteria.adicionalRamoId();
        ramoCriteria.distinct();
    }

    private static Condition<RamoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getAdicionalRamoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<RamoCriteria> copyFiltersAre(RamoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getAdicionalRamoId(), copy.getAdicionalRamoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
