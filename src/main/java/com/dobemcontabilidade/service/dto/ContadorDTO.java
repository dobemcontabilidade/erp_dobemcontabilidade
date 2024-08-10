package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Contador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContadorDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String nome;

    @NotNull
    private String crc;

    private Integer limiteEmpresas;

    private Integer limiteAreaContabils;

    private Double limiteFaturamento;

    private Integer limiteDepartamentos;

    @NotNull
    private PessoaDTO pessoa;

    @NotNull
    private PerfilContadorDTO perfilContador;

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

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public Integer getLimiteEmpresas() {
        return limiteEmpresas;
    }

    public void setLimiteEmpresas(Integer limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public Integer getLimiteAreaContabils() {
        return limiteAreaContabils;
    }

    public void setLimiteAreaContabils(Integer limiteAreaContabils) {
        this.limiteAreaContabils = limiteAreaContabils;
    }

    public Double getLimiteFaturamento() {
        return limiteFaturamento;
    }

    public void setLimiteFaturamento(Double limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    public Integer getLimiteDepartamentos() {
        return limiteDepartamentos;
    }

    public void setLimiteDepartamentos(Integer limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public PerfilContadorDTO getPerfilContador() {
        return perfilContador;
    }

    public void setPerfilContador(PerfilContadorDTO perfilContador) {
        this.perfilContador = perfilContador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContadorDTO)) {
            return false;
        }

        ContadorDTO contadorDTO = (ContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContadorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", crc='" + getCrc() + "'" +
            ", limiteEmpresas=" + getLimiteEmpresas() +
            ", limiteAreaContabils=" + getLimiteAreaContabils() +
            ", limiteFaturamento=" + getLimiteFaturamento() +
            ", limiteDepartamentos=" + getLimiteDepartamentos() +
            ", pessoa=" + getPessoa() +
            ", perfilContador=" + getPerfilContador() +
            "}";
    }
}
