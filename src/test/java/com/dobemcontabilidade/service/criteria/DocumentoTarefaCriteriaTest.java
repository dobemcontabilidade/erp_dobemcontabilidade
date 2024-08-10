package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DocumentoTarefaCriteriaTest {

    @Test
    void newDocumentoTarefaCriteriaHasAllFiltersNullTest() {
        var documentoTarefaCriteria = new DocumentoTarefaCriteria();
        assertThat(documentoTarefaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void documentoTarefaCriteriaFluentMethodsCreatesFiltersTest() {
        var documentoTarefaCriteria = new DocumentoTarefaCriteria();

        setAllFilters(documentoTarefaCriteria);

        assertThat(documentoTarefaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void documentoTarefaCriteriaCopyCreatesNullFilterTest() {
        var documentoTarefaCriteria = new DocumentoTarefaCriteria();
        var copy = documentoTarefaCriteria.copy();

        assertThat(documentoTarefaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(documentoTarefaCriteria)
        );
    }

    @Test
    void documentoTarefaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var documentoTarefaCriteria = new DocumentoTarefaCriteria();
        setAllFilters(documentoTarefaCriteria);

        var copy = documentoTarefaCriteria.copy();

        assertThat(documentoTarefaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(documentoTarefaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var documentoTarefaCriteria = new DocumentoTarefaCriteria();

        assertThat(documentoTarefaCriteria).hasToString("DocumentoTarefaCriteria{}");
    }

    private static void setAllFilters(DocumentoTarefaCriteria documentoTarefaCriteria) {
        documentoTarefaCriteria.id();
        documentoTarefaCriteria.nome();
        documentoTarefaCriteria.tarefaId();
        documentoTarefaCriteria.distinct();
    }

    private static Condition<DocumentoTarefaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getTarefaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DocumentoTarefaCriteria> copyFiltersAre(
        DocumentoTarefaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getTarefaId(), copy.getTarefaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
