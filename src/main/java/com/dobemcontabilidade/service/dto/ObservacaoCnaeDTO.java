package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.ObservacaoCnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservacaoCnaeDTO implements Serializable {

    private Long id;

    @Lob
    private String descricao;

    @NotNull
    private SubclasseCnaeDTO subclasse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SubclasseCnaeDTO getSubclasse() {
        return subclasse;
    }

    public void setSubclasse(SubclasseCnaeDTO subclasse) {
        this.subclasse = subclasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservacaoCnaeDTO)) {
            return false;
        }

        ObservacaoCnaeDTO observacaoCnaeDTO = (ObservacaoCnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, observacaoCnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservacaoCnaeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", subclasse=" + getSubclasse() +
            "}";
    }
}
