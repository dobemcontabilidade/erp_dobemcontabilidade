package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.FuncaoSocioEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Socio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SocioDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String nome;

    private Boolean prolabore;

    private Double percentualSociedade;

    @NotNull
    private Boolean adminstrador;

    private Boolean distribuicaoLucro;

    private Boolean responsavelReceita;

    private Double percentualDistribuicaoLucro;

    @NotNull
    private FuncaoSocioEnum funcaoSocio;

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

    public Boolean getProlabore() {
        return prolabore;
    }

    public void setProlabore(Boolean prolabore) {
        this.prolabore = prolabore;
    }

    public Double getPercentualSociedade() {
        return percentualSociedade;
    }

    public void setPercentualSociedade(Double percentualSociedade) {
        this.percentualSociedade = percentualSociedade;
    }

    public Boolean getAdminstrador() {
        return adminstrador;
    }

    public void setAdminstrador(Boolean adminstrador) {
        this.adminstrador = adminstrador;
    }

    public Boolean getDistribuicaoLucro() {
        return distribuicaoLucro;
    }

    public void setDistribuicaoLucro(Boolean distribuicaoLucro) {
        this.distribuicaoLucro = distribuicaoLucro;
    }

    public Boolean getResponsavelReceita() {
        return responsavelReceita;
    }

    public void setResponsavelReceita(Boolean responsavelReceita) {
        this.responsavelReceita = responsavelReceita;
    }

    public Double getPercentualDistribuicaoLucro() {
        return percentualDistribuicaoLucro;
    }

    public void setPercentualDistribuicaoLucro(Double percentualDistribuicaoLucro) {
        this.percentualDistribuicaoLucro = percentualDistribuicaoLucro;
    }

    public FuncaoSocioEnum getFuncaoSocio() {
        return funcaoSocio;
    }

    public void setFuncaoSocio(FuncaoSocioEnum funcaoSocio) {
        this.funcaoSocio = funcaoSocio;
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
        if (!(o instanceof SocioDTO)) {
            return false;
        }

        SocioDTO socioDTO = (SocioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, socioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", prolabore='" + getProlabore() + "'" +
            ", percentualSociedade=" + getPercentualSociedade() +
            ", adminstrador='" + getAdminstrador() + "'" +
            ", distribuicaoLucro='" + getDistribuicaoLucro() + "'" +
            ", responsavelReceita='" + getResponsavelReceita() + "'" +
            ", percentualDistribuicaoLucro=" + getPercentualDistribuicaoLucro() +
            ", funcaoSocio='" + getFuncaoSocio() + "'" +
            ", pessoa=" + getPessoa() +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
