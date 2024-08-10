package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.TipoSegmentoEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.SegmentoCnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SegmentoCnaeDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String nome;

    @Lob
    private String descricao;

    private String icon;

    private String imagem;

    @Lob
    private String tags;

    @NotNull
    private TipoSegmentoEnum tipo;

    private Set<SubclasseCnaeDTO> subclasseCnaes = new HashSet<>();

    @NotNull
    private RamoDTO ramo;

    private Set<EmpresaDTO> empresas = new HashSet<>();

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public TipoSegmentoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoSegmentoEnum tipo) {
        this.tipo = tipo;
    }

    public Set<SubclasseCnaeDTO> getSubclasseCnaes() {
        return subclasseCnaes;
    }

    public void setSubclasseCnaes(Set<SubclasseCnaeDTO> subclasseCnaes) {
        this.subclasseCnaes = subclasseCnaes;
    }

    public RamoDTO getRamo() {
        return ramo;
    }

    public void setRamo(RamoDTO ramo) {
        this.ramo = ramo;
    }

    public Set<EmpresaDTO> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Set<EmpresaDTO> empresas) {
        this.empresas = empresas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegmentoCnaeDTO)) {
            return false;
        }

        SegmentoCnaeDTO segmentoCnaeDTO = (SegmentoCnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, segmentoCnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SegmentoCnaeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", icon='" + getIcon() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", tags='" + getTags() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", subclasseCnaes=" + getSubclasseCnaes() +
            ", ramo=" + getRamo() +
            ", empresas=" + getEmpresas() +
            "}";
    }
}
