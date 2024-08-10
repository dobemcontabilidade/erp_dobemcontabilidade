package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioEmpresaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUsuarioEmpresaAllPropertiesEquals(UsuarioEmpresa expected, UsuarioEmpresa actual) {
        assertUsuarioEmpresaAutoGeneratedPropertiesEquals(expected, actual);
        assertUsuarioEmpresaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUsuarioEmpresaAllUpdatablePropertiesEquals(UsuarioEmpresa expected, UsuarioEmpresa actual) {
        assertUsuarioEmpresaUpdatableFieldsEquals(expected, actual);
        assertUsuarioEmpresaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUsuarioEmpresaAutoGeneratedPropertiesEquals(UsuarioEmpresa expected, UsuarioEmpresa actual) {
        assertThat(expected)
            .as("Verify UsuarioEmpresa auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUsuarioEmpresaUpdatableFieldsEquals(UsuarioEmpresa expected, UsuarioEmpresa actual) {
        assertThat(expected)
            .as("Verify UsuarioEmpresa relevant properties")
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getSenha()).as("check senha").isEqualTo(actual.getSenha()))
            .satisfies(e -> assertThat(e.getToken()).as("check token").isEqualTo(actual.getToken()))
            .satisfies(e -> assertThat(e.getDataHoraAtivacao()).as("check dataHoraAtivacao").isEqualTo(actual.getDataHoraAtivacao()))
            .satisfies(e -> assertThat(e.getDataLimiteAcesso()).as("check dataLimiteAcesso").isEqualTo(actual.getDataLimiteAcesso()))
            .satisfies(e -> assertThat(e.getSituacao()).as("check situacao").isEqualTo(actual.getSituacao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUsuarioEmpresaUpdatableRelationshipsEquals(UsuarioEmpresa expected, UsuarioEmpresa actual) {
        assertThat(expected)
            .as("Verify UsuarioEmpresa relationships")
            .satisfies(e -> assertThat(e.getPessoa()).as("check pessoa").isEqualTo(actual.getPessoa()))
            .satisfies(e -> assertThat(e.getEmpresa()).as("check empresa").isEqualTo(actual.getEmpresa()));
    }
}
