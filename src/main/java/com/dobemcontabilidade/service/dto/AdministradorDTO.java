package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Administrador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdministradorDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String nome;

    @NotNull
    @Size(max = 200)
    private String sobreNome;

    private String funcao;

    @NotNull
    private PessoaDTO pessoa;

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

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
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
        if (!(o instanceof AdministradorDTO)) {
            return false;
        }

        AdministradorDTO administradorDTO = (AdministradorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administradorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministradorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobreNome='" + getSobreNome() + "'" +
            ", funcao='" + getFuncao() + "'" +
            ", pessoa=" + getPessoa() +
            "}";
    }
}
