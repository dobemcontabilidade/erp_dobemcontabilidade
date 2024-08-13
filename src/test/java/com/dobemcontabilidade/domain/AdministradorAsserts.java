package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AdministradorAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdministradorAllPropertiesEquals(Administrador expected, Administrador actual) {
        assertAdministradorAutoGeneratedPropertiesEquals(expected, actual);
        assertAdministradorAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdministradorAllUpdatablePropertiesEquals(Administrador expected, Administrador actual) {
        assertAdministradorUpdatableFieldsEquals(expected, actual);
        assertAdministradorUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdministradorAutoGeneratedPropertiesEquals(Administrador expected, Administrador actual) {
        assertThat(expected)
            .as("Verify Administrador auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdministradorUpdatableFieldsEquals(Administrador expected, Administrador actual) {
        assertThat(expected)
            .as("Verify Administrador relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getSobrenome()).as("check sobrenome").isEqualTo(actual.getSobrenome()))
            .satisfies(e -> assertThat(e.getFuncao()).as("check funcao").isEqualTo(actual.getFuncao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdministradorUpdatableRelationshipsEquals(Administrador expected, Administrador actual) {
        assertThat(expected)
            .as("Verify Administrador relationships")
            .satisfies(e -> assertThat(e.getPessoa()).as("check pessoa").isEqualTo(actual.getPessoa()));
    }
}
