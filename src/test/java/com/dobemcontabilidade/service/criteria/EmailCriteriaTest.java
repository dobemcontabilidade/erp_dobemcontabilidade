package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmailCriteriaTest {

    @Test
    void newEmailCriteriaHasAllFiltersNullTest() {
        var emailCriteria = new EmailCriteria();
        assertThat(emailCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void emailCriteriaFluentMethodsCreatesFiltersTest() {
        var emailCriteria = new EmailCriteria();

        setAllFilters(emailCriteria);

        assertThat(emailCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void emailCriteriaCopyCreatesNullFilterTest() {
        var emailCriteria = new EmailCriteria();
        var copy = emailCriteria.copy();

        assertThat(emailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(emailCriteria)
        );
    }

    @Test
    void emailCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var emailCriteria = new EmailCriteria();
        setAllFilters(emailCriteria);

        var copy = emailCriteria.copy();

        assertThat(emailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(emailCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var emailCriteria = new EmailCriteria();

        assertThat(emailCriteria).hasToString("EmailCriteria{}");
    }

    private static void setAllFilters(EmailCriteria emailCriteria) {
        emailCriteria.id();
        emailCriteria.email();
        emailCriteria.principal();
        emailCriteria.pessoaId();
        emailCriteria.distinct();
    }

    private static Condition<EmailCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getPrincipal()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmailCriteria> copyFiltersAre(EmailCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getPrincipal(), copy.getPrincipal()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
