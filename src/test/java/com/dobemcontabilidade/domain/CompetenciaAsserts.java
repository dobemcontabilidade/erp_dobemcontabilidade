package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CompetenciaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetenciaAllPropertiesEquals(Competencia expected, Competencia actual) {
        assertCompetenciaAutoGeneratedPropertiesEquals(expected, actual);
        assertCompetenciaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetenciaAllUpdatablePropertiesEquals(Competencia expected, Competencia actual) {
        assertCompetenciaUpdatableFieldsEquals(expected, actual);
        assertCompetenciaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetenciaAutoGeneratedPropertiesEquals(Competencia expected, Competencia actual) {
        assertThat(expected)
            .as("Verify Competencia auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetenciaUpdatableFieldsEquals(Competencia expected, Competencia actual) {
        assertThat(expected)
            .as("Verify Competencia relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getNumero()).as("check numero").isEqualTo(actual.getNumero()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCompetenciaUpdatableRelationshipsEquals(Competencia expected, Competencia actual) {}
}
