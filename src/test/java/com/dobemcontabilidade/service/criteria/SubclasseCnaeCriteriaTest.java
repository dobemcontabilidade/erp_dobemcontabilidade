package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SubclasseCnaeCriteriaTest {

    @Test
    void newSubclasseCnaeCriteriaHasAllFiltersNullTest() {
        var subclasseCnaeCriteria = new SubclasseCnaeCriteria();
        assertThat(subclasseCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void subclasseCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var subclasseCnaeCriteria = new SubclasseCnaeCriteria();

        setAllFilters(subclasseCnaeCriteria);

        assertThat(subclasseCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void subclasseCnaeCriteriaCopyCreatesNullFilterTest() {
        var subclasseCnaeCriteria = new SubclasseCnaeCriteria();
        var copy = subclasseCnaeCriteria.copy();

        assertThat(subclasseCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(subclasseCnaeCriteria)
        );
    }

    @Test
    void subclasseCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var subclasseCnaeCriteria = new SubclasseCnaeCriteria();
        setAllFilters(subclasseCnaeCriteria);

        var copy = subclasseCnaeCriteria.copy();

        assertThat(subclasseCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(subclasseCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var subclasseCnaeCriteria = new SubclasseCnaeCriteria();

        assertThat(subclasseCnaeCriteria).hasToString("SubclasseCnaeCriteria{}");
    }

    private static void setAllFilters(SubclasseCnaeCriteria subclasseCnaeCriteria) {
        subclasseCnaeCriteria.id();
        subclasseCnaeCriteria.codigo();
        subclasseCnaeCriteria.anexo();
        subclasseCnaeCriteria.atendidoFreemium();
        subclasseCnaeCriteria.atendido();
        subclasseCnaeCriteria.optanteSimples();
        subclasseCnaeCriteria.aceitaMEI();
        subclasseCnaeCriteria.categoria();
        subclasseCnaeCriteria.observacaoCnaeId();
        subclasseCnaeCriteria.atividadeEmpresaId();
        subclasseCnaeCriteria.classeId();
        subclasseCnaeCriteria.segmentoCnaeId();
        subclasseCnaeCriteria.distinct();
    }

    private static Condition<SubclasseCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigo()) &&
                condition.apply(criteria.getAnexo()) &&
                condition.apply(criteria.getAtendidoFreemium()) &&
                condition.apply(criteria.getAtendido()) &&
                condition.apply(criteria.getOptanteSimples()) &&
                condition.apply(criteria.getAceitaMEI()) &&
                condition.apply(criteria.getCategoria()) &&
                condition.apply(criteria.getObservacaoCnaeId()) &&
                condition.apply(criteria.getAtividadeEmpresaId()) &&
                condition.apply(criteria.getClasseId()) &&
                condition.apply(criteria.getSegmentoCnaeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SubclasseCnaeCriteria> copyFiltersAre(
        SubclasseCnaeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigo(), copy.getCodigo()) &&
                condition.apply(criteria.getAnexo(), copy.getAnexo()) &&
                condition.apply(criteria.getAtendidoFreemium(), copy.getAtendidoFreemium()) &&
                condition.apply(criteria.getAtendido(), copy.getAtendido()) &&
                condition.apply(criteria.getOptanteSimples(), copy.getOptanteSimples()) &&
                condition.apply(criteria.getAceitaMEI(), copy.getAceitaMEI()) &&
                condition.apply(criteria.getCategoria(), copy.getCategoria()) &&
                condition.apply(criteria.getObservacaoCnaeId(), copy.getObservacaoCnaeId()) &&
                condition.apply(criteria.getAtividadeEmpresaId(), copy.getAtividadeEmpresaId()) &&
                condition.apply(criteria.getClasseId(), copy.getClasseId()) &&
                condition.apply(criteria.getSegmentoCnaeId(), copy.getSegmentoCnaeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
