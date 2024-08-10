package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.DepartamentoFuncionario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoFuncionarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String cargo;

    @NotNull
    private FuncionarioDTO funcionario;

    @NotNull
    private DepartamentoDTO departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public FuncionarioDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioDTO funcionario) {
        this.funcionario = funcionario;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartamentoFuncionarioDTO)) {
            return false;
        }

        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = (DepartamentoFuncionarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departamentoFuncionarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoFuncionarioDTO{" +
            "id=" + getId() +
            ", cargo='" + getCargo() + "'" +
            ", funcionario=" + getFuncionario() +
            ", departamento=" + getDepartamento() +
            "}";
    }
}
