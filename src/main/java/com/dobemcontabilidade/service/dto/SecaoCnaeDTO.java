package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.SecaoCnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SecaoCnaeDTO implements Serializable {

    private Long id;

    @Size(max = 15)
    private String codigo;

    @Lob
    private String descricao;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecaoCnaeDTO)) {
            return false;
        }

        SecaoCnaeDTO secaoCnaeDTO = (SecaoCnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, secaoCnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecaoCnaeDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
