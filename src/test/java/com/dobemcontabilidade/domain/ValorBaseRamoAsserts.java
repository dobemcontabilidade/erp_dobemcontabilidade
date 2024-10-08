package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ValorBaseRamoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValorBaseRamoAllPropertiesEquals(ValorBaseRamo expected, ValorBaseRamo actual) {
        assertValorBaseRamoAutoGeneratedPropertiesEquals(expected, actual);
        assertValorBaseRamoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValorBaseRamoAllUpdatablePropertiesEquals(ValorBaseRamo expected, ValorBaseRamo actual) {
        assertValorBaseRamoUpdatableFieldsEquals(expected, actual);
        assertValorBaseRamoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValorBaseRamoAutoGeneratedPropertiesEquals(ValorBaseRamo expected, ValorBaseRamo actual) {
        assertThat(expected)
            .as("Verify ValorBaseRamo auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValorBaseRamoUpdatableFieldsEquals(ValorBaseRamo expected, ValorBaseRamo actual) {
        assertThat(expected)
            .as("Verify ValorBaseRamo relevant properties")
            .satisfies(e -> assertThat(e.getValorBase()).as("check valorBase").isEqualTo(actual.getValorBase()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValorBaseRamoUpdatableRelationshipsEquals(ValorBaseRamo expected, ValorBaseRamo actual) {
        assertThat(expected)
            .as("Verify ValorBaseRamo relationships")
            .satisfies(e -> assertThat(e.getRamo()).as("check ramo").isEqualTo(actual.getRamo()))
            .satisfies(e -> assertThat(e.getPlanoContabil()).as("check planoContabil").isEqualTo(actual.getPlanoContabil()));
    }
}
