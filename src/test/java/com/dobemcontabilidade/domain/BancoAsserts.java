package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BancoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoAllPropertiesEquals(Banco expected, Banco actual) {
        assertBancoAutoGeneratedPropertiesEquals(expected, actual);
        assertBancoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoAllUpdatablePropertiesEquals(Banco expected, Banco actual) {
        assertBancoUpdatableFieldsEquals(expected, actual);
        assertBancoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoAutoGeneratedPropertiesEquals(Banco expected, Banco actual) {
        assertThat(expected)
            .as("Verify Banco auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoUpdatableFieldsEquals(Banco expected, Banco actual) {
        assertThat(expected)
            .as("Verify Banco relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getCodigo()).as("check codigo").isEqualTo(actual.getCodigo()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoUpdatableRelationshipsEquals(Banco expected, Banco actual) {}
}
