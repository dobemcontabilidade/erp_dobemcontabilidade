package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AdicionalRamoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalRamoAllPropertiesEquals(AdicionalRamo expected, AdicionalRamo actual) {
        assertAdicionalRamoAutoGeneratedPropertiesEquals(expected, actual);
        assertAdicionalRamoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalRamoAllUpdatablePropertiesEquals(AdicionalRamo expected, AdicionalRamo actual) {
        assertAdicionalRamoUpdatableFieldsEquals(expected, actual);
        assertAdicionalRamoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalRamoAutoGeneratedPropertiesEquals(AdicionalRamo expected, AdicionalRamo actual) {
        assertThat(expected)
            .as("Verify AdicionalRamo auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalRamoUpdatableFieldsEquals(AdicionalRamo expected, AdicionalRamo actual) {
        assertThat(expected)
            .as("Verify AdicionalRamo relevant properties")
            .satisfies(e -> assertThat(e.getValor()).as("check valor").isEqualTo(actual.getValor()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalRamoUpdatableRelationshipsEquals(AdicionalRamo expected, AdicionalRamo actual) {
        assertThat(expected)
            .as("Verify AdicionalRamo relationships")
            .satisfies(e -> assertThat(e.getRamo()).as("check ramo").isEqualTo(actual.getRamo()))
            .satisfies(e -> assertThat(e.getPlanoContabil()).as("check planoContabil").isEqualTo(actual.getPlanoContabil()));
    }
}
