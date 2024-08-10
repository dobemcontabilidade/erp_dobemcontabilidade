package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PerfilContadorAreaContabilAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilContadorAreaContabilAllPropertiesEquals(
        PerfilContadorAreaContabil expected,
        PerfilContadorAreaContabil actual
    ) {
        assertPerfilContadorAreaContabilAutoGeneratedPropertiesEquals(expected, actual);
        assertPerfilContadorAreaContabilAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilContadorAreaContabilAllUpdatablePropertiesEquals(
        PerfilContadorAreaContabil expected,
        PerfilContadorAreaContabil actual
    ) {
        assertPerfilContadorAreaContabilUpdatableFieldsEquals(expected, actual);
        assertPerfilContadorAreaContabilUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilContadorAreaContabilAutoGeneratedPropertiesEquals(
        PerfilContadorAreaContabil expected,
        PerfilContadorAreaContabil actual
    ) {
        assertThat(expected)
            .as("Verify PerfilContadorAreaContabil auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilContadorAreaContabilUpdatableFieldsEquals(
        PerfilContadorAreaContabil expected,
        PerfilContadorAreaContabil actual
    ) {
        assertThat(expected)
            .as("Verify PerfilContadorAreaContabil relevant properties")
            .satisfies(e -> assertThat(e.getQuantidadeEmpresas()).as("check quantidadeEmpresas").isEqualTo(actual.getQuantidadeEmpresas()))
            .satisfies(
                e -> assertThat(e.getPercentualExperiencia()).as("check percentualExperiencia").isEqualTo(actual.getPercentualExperiencia())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilContadorAreaContabilUpdatableRelationshipsEquals(
        PerfilContadorAreaContabil expected,
        PerfilContadorAreaContabil actual
    ) {
        assertThat(expected)
            .as("Verify PerfilContadorAreaContabil relationships")
            .satisfies(e -> assertThat(e.getAreaContabil()).as("check areaContabil").isEqualTo(actual.getAreaContabil()))
            .satisfies(e -> assertThat(e.getPerfilContador()).as("check perfilContador").isEqualTo(actual.getPerfilContador()));
    }
}
