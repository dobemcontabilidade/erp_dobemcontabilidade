package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OpcaoNomeFantasiaEmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Integer ordem;

    private Boolean selecionado;

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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
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
        if (!(o instanceof OpcaoNomeFantasiaEmpresaDTO)) {
            return false;
        }

        OpcaoNomeFantasiaEmpresaDTO opcaoNomeFantasiaEmpresaDTO = (OpcaoNomeFantasiaEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, opcaoNomeFantasiaEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpcaoNomeFantasiaEmpresaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ordem=" + getOrdem() +
            ", selecionado='" + getSelecionado() + "'" +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
