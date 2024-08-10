package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AnexoEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoEmpresaDTO implements Serializable {

    private Long id;

    @Lob
    private String urlAnexo;

    @NotNull
    private String tipo;

    @Lob
    private String descricao;

    @NotNull
    private EmpresaDTO empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlAnexo() {
        return urlAnexo;
    }

    public void setUrlAnexo(String urlAnexo) {
        this.urlAnexo = urlAnexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(o instanceof AnexoEmpresaDTO)) {
            return false;
        }

        AnexoEmpresaDTO anexoEmpresaDTO = (AnexoEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anexoEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoEmpresaDTO{" +
            "id=" + getId() +
            ", urlAnexo='" + getUrlAnexo() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
