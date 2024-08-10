package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Enquadramento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnquadramentoDTO implements Serializable {

    private Long id;

    private String nome;

    private String sigla;

    private Double limiteInicial;

    private Double limiteFinal;

    @Lob
    private String descricao;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Double getLimiteInicial() {
        return limiteInicial;
    }

    public void setLimiteInicial(Double limiteInicial) {
        this.limiteInicial = limiteInicial;
    }

    public Double getLimiteFinal() {
        return limiteFinal;
    }

    public void setLimiteFinal(Double limiteFinal) {
        this.limiteFinal = limiteFinal;
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
        if (!(o instanceof EnquadramentoDTO)) {
            return false;
        }

        EnquadramentoDTO enquadramentoDTO = (EnquadramentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, enquadramentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnquadramentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", limiteInicial=" + getLimiteInicial() +
            ", limiteFinal=" + getLimiteFinal() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
