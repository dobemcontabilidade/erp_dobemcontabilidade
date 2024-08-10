package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.ClasseCnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClasseCnaeDTO implements Serializable {

    private Long id;

    @Size(max = 15)
    private String codigo;

    @Lob
    private String descricao;

    @NotNull
    private GrupoCnaeDTO grupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GrupoCnaeDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoCnaeDTO grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasseCnaeDTO)) {
            return false;
        }

        ClasseCnaeDTO classeCnaeDTO = (ClasseCnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classeCnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseCnaeDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", grupo=" + getGrupo() +
            "}";
    }
}
