package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Banco} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BancoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Size(max = 20)
    private String codigo;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BancoDTO)) {
            return false;
        }

        BancoDTO bancoDTO = (BancoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bancoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BancoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
