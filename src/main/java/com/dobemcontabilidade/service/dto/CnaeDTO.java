package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Cnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CnaeDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    @Lob
    private String descricao;

    private Integer anexo;

    private Boolean atendidoFreemium;

    private Boolean atendido;

    private Boolean optanteSimples;

    private String categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAnexo() {
        return anexo;
    }

    public void setAnexo(Integer anexo) {
        this.anexo = anexo;
    }

    public Boolean getAtendidoFreemium() {
        return atendidoFreemium;
    }

    public void setAtendidoFreemium(Boolean atendidoFreemium) {
        this.atendidoFreemium = atendidoFreemium;
    }

    public Boolean getAtendido() {
        return atendido;
    }

    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    public Boolean getOptanteSimples() {
        return optanteSimples;
    }

    public void setOptanteSimples(Boolean optanteSimples) {
        this.optanteSimples = optanteSimples;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CnaeDTO)) {
            return false;
        }

        CnaeDTO cnaeDTO = (CnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CnaeDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", anexo=" + getAnexo() +
            ", atendidoFreemium='" + getAtendidoFreemium() + "'" +
            ", atendido='" + getAtendido() + "'" +
            ", optanteSimples='" + getOptanteSimples() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
