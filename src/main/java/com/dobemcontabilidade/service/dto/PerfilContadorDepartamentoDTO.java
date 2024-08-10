package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PerfilContadorDepartamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilContadorDepartamentoDTO implements Serializable {

    private Long id;

    private Integer quantidadeEmpresas;

    private Double percentualExperiencia;

    @NotNull
    private DepartamentoDTO departamento;

    @NotNull
    private PerfilContadorDTO perfilContador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeEmpresas() {
        return quantidadeEmpresas;
    }

    public void setQuantidadeEmpresas(Integer quantidadeEmpresas) {
        this.quantidadeEmpresas = quantidadeEmpresas;
    }

    public Double getPercentualExperiencia() {
        return percentualExperiencia;
    }

    public void setPercentualExperiencia(Double percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public PerfilContadorDTO getPerfilContador() {
        return perfilContador;
    }

    public void setPerfilContador(PerfilContadorDTO perfilContador) {
        this.perfilContador = perfilContador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilContadorDepartamentoDTO)) {
            return false;
        }

        PerfilContadorDepartamentoDTO perfilContadorDepartamentoDTO = (PerfilContadorDepartamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, perfilContadorDepartamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilContadorDepartamentoDTO{" +
            "id=" + getId() +
            ", quantidadeEmpresas=" + getQuantidadeEmpresas() +
            ", percentualExperiencia=" + getPercentualExperiencia() +
            ", departamento=" + getDepartamento() +
            ", perfilContador=" + getPerfilContador() +
            "}";
    }
}
