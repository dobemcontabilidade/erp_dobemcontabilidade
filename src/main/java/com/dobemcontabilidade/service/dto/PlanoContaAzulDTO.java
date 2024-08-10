package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContaAzulEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PlanoContaAzul} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoContaAzulDTO implements Serializable {

    private Long id;

    private String nome;

    private Double valorBase;

    private Integer usuarios;

    private Integer boletos;

    private Integer notaFiscalProduto;

    private Integer notaFiscalServico;

    private Integer notaFiscalCe;

    private Boolean suporte;

    private SituacaoPlanoContaAzulEnum situacao;

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

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Integer getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Integer usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getBoletos() {
        return boletos;
    }

    public void setBoletos(Integer boletos) {
        this.boletos = boletos;
    }

    public Integer getNotaFiscalProduto() {
        return notaFiscalProduto;
    }

    public void setNotaFiscalProduto(Integer notaFiscalProduto) {
        this.notaFiscalProduto = notaFiscalProduto;
    }

    public Integer getNotaFiscalServico() {
        return notaFiscalServico;
    }

    public void setNotaFiscalServico(Integer notaFiscalServico) {
        this.notaFiscalServico = notaFiscalServico;
    }

    public Integer getNotaFiscalCe() {
        return notaFiscalCe;
    }

    public void setNotaFiscalCe(Integer notaFiscalCe) {
        this.notaFiscalCe = notaFiscalCe;
    }

    public Boolean getSuporte() {
        return suporte;
    }

    public void setSuporte(Boolean suporte) {
        this.suporte = suporte;
    }

    public SituacaoPlanoContaAzulEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPlanoContaAzulEnum situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoContaAzulDTO)) {
            return false;
        }

        PlanoContaAzulDTO planoContaAzulDTO = (PlanoContaAzulDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planoContaAzulDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoContaAzulDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valorBase=" + getValorBase() +
            ", usuarios=" + getUsuarios() +
            ", boletos=" + getBoletos() +
            ", notaFiscalProduto=" + getNotaFiscalProduto() +
            ", notaFiscalServico=" + getNotaFiscalServico() +
            ", notaFiscalCe=" + getNotaFiscalCe() +
            ", suporte='" + getSuporte() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
