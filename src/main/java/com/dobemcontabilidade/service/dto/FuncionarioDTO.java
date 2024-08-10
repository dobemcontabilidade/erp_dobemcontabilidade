package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoFuncionarioEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Funcionario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuncionarioDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String nome;

    private Double salario;

    private String ctps;

    private String cargo;

    @Lob
    private String descricaoAtividades;

    private SituacaoFuncionarioEnum situacao;

    @NotNull
    private PessoaDTO pessoa;

    @NotNull
    private EmpresaDTO empresa;

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

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescricaoAtividades() {
        return descricaoAtividades;
    }

    public void setDescricaoAtividades(String descricaoAtividades) {
        this.descricaoAtividades = descricaoAtividades;
    }

    public SituacaoFuncionarioEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoFuncionarioEnum situacao) {
        this.situacao = situacao;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncionarioDTO)) {
            return false;
        }

        FuncionarioDTO funcionarioDTO = (FuncionarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, funcionarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", salario=" + getSalario() +
            ", ctps='" + getCtps() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", descricaoAtividades='" + getDescricaoAtividades() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", pessoa=" + getPessoa() +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
