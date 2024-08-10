package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AnexoPessoa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoPessoaDTO implements Serializable {

    private Long id;

    @Lob
    private String urlArquivo;

    private String tipo;

    @Lob
    private String descricao;

    @NotNull
    private PessoaDTO pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoPessoaDTO)) {
            return false;
        }

        AnexoPessoaDTO anexoPessoaDTO = (AnexoPessoaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anexoPessoaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoPessoaDTO{" +
            "id=" + getId() +
            ", urlArquivo='" + getUrlArquivo() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", pessoa=" + getPessoa() +
            "}";
    }
}
