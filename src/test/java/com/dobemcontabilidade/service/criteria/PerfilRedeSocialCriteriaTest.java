package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PerfilRedeSocialCriteriaTest {

    @Test
    void newPerfilRedeSocialCriteriaHasAllFiltersNullTest() {
        var perfilRedeSocialCriteria = new PerfilRedeSocialCriteria();
        assertThat(perfilRedeSocialCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void perfilRedeSocialCriteriaFluentMethodsCreatesFiltersTest() {
        var perfilRedeSocialCriteria = new PerfilRedeSocialCriteria();

        setAllFilters(perfilRedeSocialCriteria);

        assertThat(perfilRedeSocialCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void perfilRedeSocialCriteriaCopyCreatesNullFilterTest() {
        var perfilRedeSocialCriteria = new PerfilRedeSocialCriteria();
        var copy = perfilRedeSocialCriteria.copy();

        assertThat(perfilRedeSocialCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(perfilRedeSocialCriteria)
        );
    }

    @Test
    void perfilRedeSocialCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var perfilRedeSocialCriteria = new PerfilRedeSocialCriteria();
        setAllFilters(perfilRedeSocialCriteria);

        var copy = perfilRedeSocialCriteria.copy();

        assertThat(perfilRedeSocialCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(perfilRedeSocialCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var perfilRedeSocialCriteria = new PerfilRedeSocialCriteria();

        assertThat(perfilRedeSocialCriteria).hasToString("PerfilRedeSocialCriteria{}");
    }

    private static void setAllFilters(PerfilRedeSocialCriteria perfilRedeSocialCriteria) {
        perfilRedeSocialCriteria.id();
        perfilRedeSocialCriteria.rede();
        perfilRedeSocialCriteria.urlPerfil();
        perfilRedeSocialCriteria.tipoRede();
        perfilRedeSocialCriteria.distinct();
    }

    private static Condition<PerfilRedeSocialCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRede()) &&
                condition.apply(criteria.getUrlPerfil()) &&
                condition.apply(criteria.getTipoRede()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PerfilRedeSocialCriteria> copyFiltersAre(
        PerfilRedeSocialCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRede(), copy.getRede()) &&
                condition.apply(criteria.getUrlPerfil(), copy.getUrlPerfil()) &&
                condition.apply(criteria.getTipoRede(), copy.getTipoRede()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
