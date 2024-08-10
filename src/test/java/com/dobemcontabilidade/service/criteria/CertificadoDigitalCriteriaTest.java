package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CertificadoDigitalCriteriaTest {

    @Test
    void newCertificadoDigitalCriteriaHasAllFiltersNullTest() {
        var certificadoDigitalCriteria = new CertificadoDigitalCriteria();
        assertThat(certificadoDigitalCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void certificadoDigitalCriteriaFluentMethodsCreatesFiltersTest() {
        var certificadoDigitalCriteria = new CertificadoDigitalCriteria();

        setAllFilters(certificadoDigitalCriteria);

        assertThat(certificadoDigitalCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void certificadoDigitalCriteriaCopyCreatesNullFilterTest() {
        var certificadoDigitalCriteria = new CertificadoDigitalCriteria();
        var copy = certificadoDigitalCriteria.copy();

        assertThat(certificadoDigitalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(certificadoDigitalCriteria)
        );
    }

    @Test
    void certificadoDigitalCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var certificadoDigitalCriteria = new CertificadoDigitalCriteria();
        setAllFilters(certificadoDigitalCriteria);

        var copy = certificadoDigitalCriteria.copy();

        assertThat(certificadoDigitalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(certificadoDigitalCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var certificadoDigitalCriteria = new CertificadoDigitalCriteria();

        assertThat(certificadoDigitalCriteria).hasToString("CertificadoDigitalCriteria{}");
    }

    private static void setAllFilters(CertificadoDigitalCriteria certificadoDigitalCriteria) {
        certificadoDigitalCriteria.id();
        certificadoDigitalCriteria.dataContratacao();
        certificadoDigitalCriteria.validade();
        certificadoDigitalCriteria.tipoCertificado();
        certificadoDigitalCriteria.empresaId();
        certificadoDigitalCriteria.distinct();
    }

    private static Condition<CertificadoDigitalCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDataContratacao()) &&
                condition.apply(criteria.getValidade()) &&
                condition.apply(criteria.getTipoCertificado()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CertificadoDigitalCriteria> copyFiltersAre(
        CertificadoDigitalCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDataContratacao(), copy.getDataContratacao()) &&
                condition.apply(criteria.getValidade(), copy.getValidade()) &&
                condition.apply(criteria.getTipoCertificado(), copy.getTipoCertificado()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
