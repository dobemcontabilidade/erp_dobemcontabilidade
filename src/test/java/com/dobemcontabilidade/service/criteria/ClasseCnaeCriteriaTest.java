package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ClasseCnaeCriteriaTest {

    @Test
    void newClasseCnaeCriteriaHasAllFiltersNullTest() {
        var classeCnaeCriteria = new ClasseCnaeCriteria();
        assertThat(classeCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void classeCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var classeCnaeCriteria = new ClasseCnaeCriteria();

        setAllFilters(classeCnaeCriteria);

        assertThat(classeCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void classeCnaeCriteriaCopyCreatesNullFilterTest() {
        var classeCnaeCriteria = new ClasseCnaeCriteria();
        var copy = classeCnaeCriteria.copy();

        assertThat(classeCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(classeCnaeCriteria)
        );
    }

    @Test
    void classeCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var classeCnaeCriteria = new ClasseCnaeCriteria();
        setAllFilters(classeCnaeCriteria);

        var copy = classeCnaeCriteria.copy();

        assertThat(classeCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(classeCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var classeCnaeCriteria = new ClasseCnaeCriteria();

        assertThat(classeCnaeCriteria).hasToString("ClasseCnaeCriteria{}");
    }

    private static void setAllFilters(ClasseCnaeCriteria classeCnaeCriteria) {
        classeCnaeCriteria.id();
        classeCnaeCriteria.codigo();
        classeCnaeCriteria.subclasseCnaeId();
        classeCnaeCriteria.grupoId();
        classeCnaeCriteria.distinct();
    }

    private static Condition<ClasseCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigo()) &&
                condition.apply(criteria.getSubclasseCnaeId()) &&
                condition.apply(criteria.getGrupoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ClasseCnaeCriteria> copyFiltersAre(ClasseCnaeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigo(), copy.getCodigo()) &&
                condition.apply(criteria.getSubclasseCnaeId(), copy.getSubclasseCnaeId()) &&
                condition.apply(criteria.getGrupoId(), copy.getGrupoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
