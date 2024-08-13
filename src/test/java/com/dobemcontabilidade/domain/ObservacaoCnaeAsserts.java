package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservacaoCnaeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertObservacaoCnaeAllPropertiesEquals(ObservacaoCnae expected, ObservacaoCnae actual) {
        assertObservacaoCnaeAutoGeneratedPropertiesEquals(expected, actual);
        assertObservacaoCnaeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertObservacaoCnaeAllUpdatablePropertiesEquals(ObservacaoCnae expected, ObservacaoCnae actual) {
        assertObservacaoCnaeUpdatableFieldsEquals(expected, actual);
        assertObservacaoCnaeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertObservacaoCnaeAutoGeneratedPropertiesEquals(ObservacaoCnae expected, ObservacaoCnae actual) {
        assertThat(expected)
            .as("Verify ObservacaoCnae auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertObservacaoCnaeUpdatableFieldsEquals(ObservacaoCnae expected, ObservacaoCnae actual) {
        assertThat(expected)
            .as("Verify ObservacaoCnae relevant properties")
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertObservacaoCnaeUpdatableRelationshipsEquals(ObservacaoCnae expected, ObservacaoCnae actual) {
        assertThat(expected)
            .as("Verify ObservacaoCnae relationships")
            .satisfies(e -> assertThat(e.getSubclasse()).as("check subclasse").isEqualTo(actual.getSubclasse()));
    }
}
