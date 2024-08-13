package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PerfilAcessoUsuarioAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilAcessoUsuarioAllPropertiesEquals(PerfilAcessoUsuario expected, PerfilAcessoUsuario actual) {
        assertPerfilAcessoUsuarioAutoGeneratedPropertiesEquals(expected, actual);
        assertPerfilAcessoUsuarioAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilAcessoUsuarioAllUpdatablePropertiesEquals(PerfilAcessoUsuario expected, PerfilAcessoUsuario actual) {
        assertPerfilAcessoUsuarioUpdatableFieldsEquals(expected, actual);
        assertPerfilAcessoUsuarioUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilAcessoUsuarioAutoGeneratedPropertiesEquals(PerfilAcessoUsuario expected, PerfilAcessoUsuario actual) {
        assertThat(expected)
            .as("Verify PerfilAcessoUsuario auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilAcessoUsuarioUpdatableFieldsEquals(PerfilAcessoUsuario expected, PerfilAcessoUsuario actual) {
        assertThat(expected)
            .as("Verify PerfilAcessoUsuario relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getAutorizado()).as("check autorizado").isEqualTo(actual.getAutorizado()))
            .satisfies(e -> assertThat(e.getDataExpiracao()).as("check dataExpiracao").isEqualTo(actual.getDataExpiracao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerfilAcessoUsuarioUpdatableRelationshipsEquals(PerfilAcessoUsuario expected, PerfilAcessoUsuario actual) {}
}
