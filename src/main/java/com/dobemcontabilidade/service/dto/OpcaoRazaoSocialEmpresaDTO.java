package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OpcaoRazaoSocialEmpresaDTO implements Serializable {

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
        if (!(o instanceof OpcaoRazaoSocialEmpresaDTO)) {
            return false;
        }

        OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO = (OpcaoRazaoSocialEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, opcaoRazaoSocialEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpcaoRazaoSocialEmpresaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ordem=" + getOrdem() +
            ", selecionado='" + getSelecionado() + "'" +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
