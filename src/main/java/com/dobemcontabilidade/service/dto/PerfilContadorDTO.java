package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PerfilContador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilContadorDTO implements Serializable {

    private Long id;

    @NotNull
    private String perfil;

    private String descricao;

    private Integer limiteEmpresas;

    private Integer limiteDepartamentos;

    private Integer limiteAreaContabils;

    private Double limiteFaturamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLimiteEmpresas() {
        return limiteEmpresas;
    }

    public void setLimiteEmpresas(Integer limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public Integer getLimiteDepartamentos() {
        return limiteDepartamentos;
    }

    public void setLimiteDepartamentos(Integer limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public Integer getLimiteAreaContabils() {
        return limiteAreaContabils;
    }

    public void setLimiteAreaContabils(Integer limiteAreaContabils) {
        this.limiteAreaContabils = limiteAreaContabils;
    }

    public Double getLimiteFaturamento() {
        return limiteFaturamento;
    }

    public void setLimiteFaturamento(Double limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilContadorDTO)) {
            return false;
        }

        PerfilContadorDTO perfilContadorDTO = (PerfilContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, perfilContadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilContadorDTO{" +
            "id=" + getId() +
            ", perfil='" + getPerfil() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", limiteEmpresas=" + getLimiteEmpresas() +
            ", limiteDepartamentos=" + getLimiteDepartamentos() +
            ", limiteAreaContabils=" + getLimiteAreaContabils() +
            ", limiteFaturamento=" + getLimiteFaturamento() +
            "}";
    }
}
