package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Profissao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfissaoDTO implements Serializable {

    private Long id;

    private String nome;

    @Lob
    private String descricao;

    @NotNull
    private SocioDTO socio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SocioDTO getSocio() {
        return socio;
    }

    public void setSocio(SocioDTO socio) {
        this.socio = socio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfissaoDTO)) {
            return false;
        }

        ProfissaoDTO profissaoDTO = (ProfissaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profissaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfissaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", socio=" + getSocio() +
            "}";
    }
}
