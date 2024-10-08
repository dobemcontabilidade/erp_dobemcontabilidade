package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FuncionarioAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionarioAllPropertiesEquals(Funcionario expected, Funcionario actual) {
        assertFuncionarioAutoGeneratedPropertiesEquals(expected, actual);
        assertFuncionarioAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionarioAllUpdatablePropertiesEquals(Funcionario expected, Funcionario actual) {
        assertFuncionarioUpdatableFieldsEquals(expected, actual);
        assertFuncionarioUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionarioAutoGeneratedPropertiesEquals(Funcionario expected, Funcionario actual) {
        assertThat(expected)
            .as("Verify Funcionario auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionarioUpdatableFieldsEquals(Funcionario expected, Funcionario actual) {
        assertThat(expected)
            .as("Verify Funcionario relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getSalario()).as("check salario").isEqualTo(actual.getSalario()))
            .satisfies(e -> assertThat(e.getCtps()).as("check ctps").isEqualTo(actual.getCtps()))
            .satisfies(e -> assertThat(e.getCargo()).as("check cargo").isEqualTo(actual.getCargo()))
            .satisfies(
                e -> assertThat(e.getDescricaoAtividades()).as("check descricaoAtividades").isEqualTo(actual.getDescricaoAtividades())
            )
            .satisfies(e -> assertThat(e.getSituacao()).as("check situacao").isEqualTo(actual.getSituacao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionarioUpdatableRelationshipsEquals(Funcionario expected, Funcionario actual) {
        assertThat(expected)
            .as("Verify Funcionario relationships")
            .satisfies(e -> assertThat(e.getPessoa()).as("check pessoa").isEqualTo(actual.getPessoa()))
            .satisfies(e -> assertThat(e.getEmpresa()).as("check empresa").isEqualTo(actual.getEmpresa()));
    }
}
