package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EnquadramentoCriteriaTest {

    @Test
    void newEnquadramentoCriteriaHasAllFiltersNullTest() {
        var enquadramentoCriteria = new EnquadramentoCriteria();
        assertThat(enquadramentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void enquadramentoCriteriaFluentMethodsCreatesFiltersTest() {
        var enquadramentoCriteria = new EnquadramentoCriteria();

        setAllFilters(enquadramentoCriteria);

        assertThat(enquadramentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void enquadramentoCriteriaCopyCreatesNullFilterTest() {
        var enquadramentoCriteria = new EnquadramentoCriteria();
        var copy = enquadramentoCriteria.copy();

        assertThat(enquadramentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(enquadramentoCriteria)
        );
    }

    @Test
    void enquadramentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var enquadramentoCriteria = new EnquadramentoCriteria();
        setAllFilters(enquadramentoCriteria);

        var copy = enquadramentoCriteria.copy();

        assertThat(enquadramentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(enquadramentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var enquadramentoCriteria = new EnquadramentoCriteria();

        assertThat(enquadramentoCriteria).hasToString("EnquadramentoCriteria{}");
    }

    private static void setAllFilters(EnquadramentoCriteria enquadramentoCriteria) {
        enquadramentoCriteria.id();
        enquadramentoCriteria.nome();
        enquadramentoCriteria.sigla();
        enquadramentoCriteria.limiteInicial();
        enquadramentoCriteria.limiteFinal();
        enquadramentoCriteria.empresaId();
        enquadramentoCriteria.adicionalEnquadramentoId();
        enquadramentoCriteria.distinct();
    }

    private static Condition<EnquadramentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getSigla()) &&
                condition.apply(criteria.getLimiteInicial()) &&
                condition.apply(criteria.getLimiteFinal()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getAdicionalEnquadramentoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EnquadramentoCriteria> copyFiltersAre(
        EnquadramentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getSigla(), copy.getSigla()) &&
                condition.apply(criteria.getLimiteInicial(), copy.getLimiteInicial()) &&
                condition.apply(criteria.getLimiteFinal(), copy.getLimiteFinal()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getAdicionalEnquadramentoId(), copy.getAdicionalEnquadramentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
