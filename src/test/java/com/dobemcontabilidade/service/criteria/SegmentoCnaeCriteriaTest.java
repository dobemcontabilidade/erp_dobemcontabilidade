package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SegmentoCnaeCriteriaTest {

    @Test
    void newSegmentoCnaeCriteriaHasAllFiltersNullTest() {
        var segmentoCnaeCriteria = new SegmentoCnaeCriteria();
        assertThat(segmentoCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void segmentoCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var segmentoCnaeCriteria = new SegmentoCnaeCriteria();

        setAllFilters(segmentoCnaeCriteria);

        assertThat(segmentoCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void segmentoCnaeCriteriaCopyCreatesNullFilterTest() {
        var segmentoCnaeCriteria = new SegmentoCnaeCriteria();
        var copy = segmentoCnaeCriteria.copy();

        assertThat(segmentoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(segmentoCnaeCriteria)
        );
    }

    @Test
    void segmentoCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var segmentoCnaeCriteria = new SegmentoCnaeCriteria();
        setAllFilters(segmentoCnaeCriteria);

        var copy = segmentoCnaeCriteria.copy();

        assertThat(segmentoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(segmentoCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var segmentoCnaeCriteria = new SegmentoCnaeCriteria();

        assertThat(segmentoCnaeCriteria).hasToString("SegmentoCnaeCriteria{}");
    }

    private static void setAllFilters(SegmentoCnaeCriteria segmentoCnaeCriteria) {
        segmentoCnaeCriteria.id();
        segmentoCnaeCriteria.nome();
        segmentoCnaeCriteria.icon();
        segmentoCnaeCriteria.imagem();
        segmentoCnaeCriteria.tipo();
        segmentoCnaeCriteria.subclasseCnaeId();
        segmentoCnaeCriteria.ramoId();
        segmentoCnaeCriteria.empresaId();
        segmentoCnaeCriteria.distinct();
    }

    private static Condition<SegmentoCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getIcon()) &&
                condition.apply(criteria.getImagem()) &&
                condition.apply(criteria.getTipo()) &&
                condition.apply(criteria.getSubclasseCnaeId()) &&
                condition.apply(criteria.getRamoId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SegmentoCnaeCriteria> copyFiltersAre(
        SegmentoCnaeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getIcon(), copy.getIcon()) &&
                condition.apply(criteria.getImagem(), copy.getImagem()) &&
                condition.apply(criteria.getTipo(), copy.getTipo()) &&
                condition.apply(criteria.getSubclasseCnaeId(), copy.getSubclasseCnaeId()) &&
                condition.apply(criteria.getRamoId(), copy.getRamoId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
