package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TermoDeAdesaoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoDeAdesaoAllPropertiesEquals(TermoDeAdesao expected, TermoDeAdesao actual) {
        assertTermoDeAdesaoAutoGeneratedPropertiesEquals(expected, actual);
        assertTermoDeAdesaoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoDeAdesaoAllUpdatablePropertiesEquals(TermoDeAdesao expected, TermoDeAdesao actual) {
        assertTermoDeAdesaoUpdatableFieldsEquals(expected, actual);
        assertTermoDeAdesaoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoDeAdesaoAutoGeneratedPropertiesEquals(TermoDeAdesao expected, TermoDeAdesao actual) {
        assertThat(expected)
            .as("Verify TermoDeAdesao auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoDeAdesaoUpdatableFieldsEquals(TermoDeAdesao expected, TermoDeAdesao actual) {
        assertThat(expected)
            .as("Verify TermoDeAdesao relevant properties")
            .satisfies(e -> assertThat(e.getTitulo()).as("check titulo").isEqualTo(actual.getTitulo()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoDeAdesaoUpdatableRelationshipsEquals(TermoDeAdesao expected, TermoDeAdesao actual) {}
}
