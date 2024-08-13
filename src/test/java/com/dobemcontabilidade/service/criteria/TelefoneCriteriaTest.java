package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TelefoneCriteriaTest {

    @Test
    void newTelefoneCriteriaHasAllFiltersNullTest() {
        var telefoneCriteria = new TelefoneCriteria();
        assertThat(telefoneCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void telefoneCriteriaFluentMethodsCreatesFiltersTest() {
        var telefoneCriteria = new TelefoneCriteria();

        setAllFilters(telefoneCriteria);

        assertThat(telefoneCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void telefoneCriteriaCopyCreatesNullFilterTest() {
        var telefoneCriteria = new TelefoneCriteria();
        var copy = telefoneCriteria.copy();

        assertThat(telefoneCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(telefoneCriteria)
        );
    }

    @Test
    void telefoneCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var telefoneCriteria = new TelefoneCriteria();
        setAllFilters(telefoneCriteria);

        var copy = telefoneCriteria.copy();

        assertThat(telefoneCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(telefoneCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var telefoneCriteria = new TelefoneCriteria();

        assertThat(telefoneCriteria).hasToString("TelefoneCriteria{}");
    }

    private static void setAllFilters(TelefoneCriteria telefoneCriteria) {
        telefoneCriteria.id();
        telefoneCriteria.codigoArea();
        telefoneCriteria.telefone();
        telefoneCriteria.principal();
        telefoneCriteria.tipoTelefone();
        telefoneCriteria.pessoaId();
        telefoneCriteria.distinct();
    }

    private static Condition<TelefoneCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigoArea()) &&
                condition.apply(criteria.getTelefone()) &&
                condition.apply(criteria.getPrincipal()) &&
                condition.apply(criteria.getTipoTelefone()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TelefoneCriteria> copyFiltersAre(TelefoneCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigoArea(), copy.getCodigoArea()) &&
                condition.apply(criteria.getTelefone(), copy.getTelefone()) &&
                condition.apply(criteria.getPrincipal(), copy.getPrincipal()) &&
                condition.apply(criteria.getTipoTelefone(), copy.getTipoTelefone()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
