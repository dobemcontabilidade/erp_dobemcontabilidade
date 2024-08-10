package com.dobemcontabilidade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Estado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDTO implements Serializable {

    private Long id;

    private String nome;

    private String naturalidade;

    private String sigla;

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

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoDTO)) {
            return false;
        }

        EstadoDTO estadoDTO = (EstadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
