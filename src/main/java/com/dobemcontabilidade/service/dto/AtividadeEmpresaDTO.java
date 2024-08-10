package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AtividadeEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AtividadeEmpresaDTO implements Serializable {

    private Long id;

    private Boolean principal;

    private Integer ordem;

    private String descricaoAtividade;

    @NotNull
    private EmpresaDTO empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
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
        if (!(o instanceof AtividadeEmpresaDTO)) {
            return false;
        }

        AtividadeEmpresaDTO atividadeEmpresaDTO = (AtividadeEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, atividadeEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtividadeEmpresaDTO{" +
            "id=" + getId() +
            ", principal='" + getPrincipal() + "'" +
            ", ordem=" + getOrdem() +
            ", descricaoAtividade='" + getDescricaoAtividade() + "'" +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
