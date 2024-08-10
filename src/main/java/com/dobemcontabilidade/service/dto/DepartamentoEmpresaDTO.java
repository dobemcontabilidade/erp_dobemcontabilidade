package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.DepartamentoEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoEmpresaDTO implements Serializable {

    private Long id;

    private Double pontuacao;

    private String depoimento;

    private String reclamacao;

    @NotNull
    private DepartamentoDTO departamento;

    @NotNull
    private EmpresaDTO empresa;

    @NotNull
    private ContadorDTO contador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getDepoimento() {
        return depoimento;
    }

    public void setDepoimento(String depoimento) {
        this.depoimento = depoimento;
    }

    public String getReclamacao() {
        return reclamacao;
    }

    public void setReclamacao(String reclamacao) {
        this.reclamacao = reclamacao;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartamentoEmpresaDTO)) {
            return false;
        }

        DepartamentoEmpresaDTO departamentoEmpresaDTO = (DepartamentoEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departamentoEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoEmpresaDTO{" +
            "id=" + getId() +
            ", pontuacao=" + getPontuacao() +
            ", depoimento='" + getDepoimento() + "'" +
            ", reclamacao='" + getReclamacao() + "'" +
            ", departamento=" + getDepartamento() +
            ", empresa=" + getEmpresa() +
            ", contador=" + getContador() +
            "}";
    }
}
