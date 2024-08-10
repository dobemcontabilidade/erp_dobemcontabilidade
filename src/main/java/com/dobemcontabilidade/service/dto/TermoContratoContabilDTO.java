package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.TermoContratoContabil} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoContratoContabilDTO implements Serializable {

    private Long id;

    private String documento;

    @Lob
    private String descricao;

    private String titulo;

    @NotNull
    private PlanoContabilDTO planoContabil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public PlanoContabilDTO getPlanoContabil() {
        return planoContabil;
    }

    public void setPlanoContabil(PlanoContabilDTO planoContabil) {
        this.planoContabil = planoContabil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoContratoContabilDTO)) {
            return false;
        }

        TermoContratoContabilDTO termoContratoContabilDTO = (TermoContratoContabilDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, termoContratoContabilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoContratoContabilDTO{" +
            "id=" + getId() +
            ", documento='" + getDocumento() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", planoContabil=" + getPlanoContabil() +
            "}";
    }
}
