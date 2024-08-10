package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Pessoa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PessoaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nome;

    @NotNull
    private String cpf;

    private Instant dataNascimento;

    private String tituloEleitor;

    @NotNull
    private String rg;

    private String rgOrgaoExpditor;

    private String rgUfExpedicao;

    private EstadoCivilEnum estadoCivil;

    @NotNull
    private SexoEnum sexo;

    private String urlFotoPerfil;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgOrgaoExpditor() {
        return rgOrgaoExpditor;
    }

    public void setRgOrgaoExpditor(String rgOrgaoExpditor) {
        this.rgOrgaoExpditor = rgOrgaoExpditor;
    }

    public String getRgUfExpedicao() {
        return rgUfExpedicao;
    }

    public void setRgUfExpedicao(String rgUfExpedicao) {
        this.rgUfExpedicao = rgUfExpedicao;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaDTO)) {
            return false;
        }

        PessoaDTO pessoaDTO = (PessoaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pessoaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", tituloEleitor='" + getTituloEleitor() + "'" +
            ", rg='" + getRg() + "'" +
            ", rgOrgaoExpditor='" + getRgOrgaoExpditor() + "'" +
            ", rgUfExpedicao='" + getRgUfExpedicao() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", urlFotoPerfil='" + getUrlFotoPerfil() + "'" +
            "}";
    }
}
