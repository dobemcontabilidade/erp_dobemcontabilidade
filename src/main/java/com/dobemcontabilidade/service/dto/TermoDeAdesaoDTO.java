package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.TermoDeAdesao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoDeAdesaoDTO implements Serializable {

    private Long id;

    private String titulo;

    @Lob
    private String descricao;

    private String urlDoc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlDoc() {
        return urlDoc;
    }

    public void setUrlDoc(String urlDoc) {
        this.urlDoc = urlDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoDeAdesaoDTO)) {
            return false;
        }

        TermoDeAdesaoDTO termoDeAdesaoDTO = (TermoDeAdesaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, termoDeAdesaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoDeAdesaoDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", urlDoc='" + getUrlDoc() + "'" +
            "}";
    }
}
