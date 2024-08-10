package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Frequencia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FrequenciaDTO implements Serializable {

    private Long id;

    private String nome;

    private String prioridade;

    @Lob
    private String descricao;

    private Integer numeroDias;

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

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FrequenciaDTO)) {
            return false;
        }

        FrequenciaDTO frequenciaDTO = (FrequenciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, frequenciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FrequenciaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", numeroDias=" + getNumeroDias() +
            "}";
    }
}
