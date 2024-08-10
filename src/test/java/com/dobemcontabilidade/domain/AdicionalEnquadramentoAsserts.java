package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AdicionalEnquadramentoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalEnquadramentoAllPropertiesEquals(AdicionalEnquadramento expected, AdicionalEnquadramento actual) {
        assertAdicionalEnquadramentoAutoGeneratedPropertiesEquals(expected, actual);
        assertAdicionalEnquadramentoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalEnquadramentoAllUpdatablePropertiesEquals(
        AdicionalEnquadramento expected,
        AdicionalEnquadramento actual
    ) {
        assertAdicionalEnquadramentoUpdatableFieldsEquals(expected, actual);
        assertAdicionalEnquadramentoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalEnquadramentoAutoGeneratedPropertiesEquals(
        AdicionalEnquadramento expected,
        AdicionalEnquadramento actual
    ) {
        assertThat(expected)
            .as("Verify AdicionalEnquadramento auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalEnquadramentoUpdatableFieldsEquals(AdicionalEnquadramento expected, AdicionalEnquadramento actual) {
        assertThat(expected)
            .as("Verify AdicionalEnquadramento relevant properties")
            .satisfies(e -> assertThat(e.getValor()).as("check valor").isEqualTo(actual.getValor()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalEnquadramentoUpdatableRelationshipsEquals(
        AdicionalEnquadramento expected,
        AdicionalEnquadramento actual
    ) {
        assertThat(expected)
            .as("Verify AdicionalEnquadramento relationships")
            .satisfies(e -> assertThat(e.getEnquadramento()).as("check enquadramento").isEqualTo(actual.getEnquadramento()))
            .satisfies(e -> assertThat(e.getPlanoContabil()).as("check planoContabil").isEqualTo(actual.getPlanoContabil()));
    }
}
