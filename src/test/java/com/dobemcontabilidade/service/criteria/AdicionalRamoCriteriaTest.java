package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AdicionalRamoCriteriaTest {

    @Test
    void newAdicionalRamoCriteriaHasAllFiltersNullTest() {
        var adicionalRamoCriteria = new AdicionalRamoCriteria();
        assertThat(adicionalRamoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void adicionalRamoCriteriaFluentMethodsCreatesFiltersTest() {
        var adicionalRamoCriteria = new AdicionalRamoCriteria();

        setAllFilters(adicionalRamoCriteria);

        assertThat(adicionalRamoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void adicionalRamoCriteriaCopyCreatesNullFilterTest() {
        var adicionalRamoCriteria = new AdicionalRamoCriteria();
        var copy = adicionalRamoCriteria.copy();

        assertThat(adicionalRamoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(adicionalRamoCriteria)
        );
    }

    @Test
    void adicionalRamoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var adicionalRamoCriteria = new AdicionalRamoCriteria();
        setAllFilters(adicionalRamoCriteria);

        var copy = adicionalRamoCriteria.copy();

        assertThat(adicionalRamoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(adicionalRamoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var adicionalRamoCriteria = new AdicionalRamoCriteria();

        assertThat(adicionalRamoCriteria).hasToString("AdicionalRamoCriteria{}");
    }

    private static void setAllFilters(AdicionalRamoCriteria adicionalRamoCriteria) {
        adicionalRamoCriteria.id();
        adicionalRamoCriteria.valor();
        adicionalRamoCriteria.ramoId();
        adicionalRamoCriteria.planoAssinaturaContabilId();
        adicionalRamoCriteria.distinct();
    }

    private static Condition<AdicionalRamoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getValor()) &&
                condition.apply(criteria.getRamoId()) &&
                condition.apply(criteria.getPlanoAssinaturaContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AdicionalRamoCriteria> copyFiltersAre(
        AdicionalRamoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getValor(), copy.getValor()) &&
                condition.apply(criteria.getRamoId(), copy.getRamoId()) &&
                condition.apply(criteria.getPlanoAssinaturaContabilId(), copy.getPlanoAssinaturaContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
