package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TelefoneAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoneAllPropertiesEquals(Telefone expected, Telefone actual) {
        assertTelefoneAutoGeneratedPropertiesEquals(expected, actual);
        assertTelefoneAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoneAllUpdatablePropertiesEquals(Telefone expected, Telefone actual) {
        assertTelefoneUpdatableFieldsEquals(expected, actual);
        assertTelefoneUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoneAutoGeneratedPropertiesEquals(Telefone expected, Telefone actual) {
        assertThat(expected)
            .as("Verify Telefone auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoneUpdatableFieldsEquals(Telefone expected, Telefone actual) {
        assertThat(expected)
            .as("Verify Telefone relevant properties")
            .satisfies(e -> assertThat(e.getCodigoArea()).as("check codigoArea").isEqualTo(actual.getCodigoArea()))
            .satisfies(e -> assertThat(e.getTelefone()).as("check telefone").isEqualTo(actual.getTelefone()))
            .satisfies(e -> assertThat(e.getPrincipla()).as("check principla").isEqualTo(actual.getPrincipla()))
            .satisfies(e -> assertThat(e.getTipoTelefone()).as("check tipoTelefone").isEqualTo(actual.getTipoTelefone()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoneUpdatableRelationshipsEquals(Telefone expected, Telefone actual) {
        assertThat(expected)
            .as("Verify Telefone relationships")
            .satisfies(e -> assertThat(e.getPessoa()).as("check pessoa").isEqualTo(actual.getPessoa()));
    }
}
