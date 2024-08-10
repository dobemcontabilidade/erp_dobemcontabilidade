package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoTributacaoEnum;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Tributacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TributacaoDTO implements Serializable {

    private Long id;

    private String nome;

    @Lob
    private String descricao;

    private SituacaoTributacaoEnum situacao;

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

    public SituacaoTributacaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoTributacaoEnum situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TributacaoDTO)) {
            return false;
        }

        TributacaoDTO tributacaoDTO = (TributacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tributacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TributacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
