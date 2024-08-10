package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.SubclasseCnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubclasseCnaeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String codigo;

    @Lob
    private String descricao;

    private Integer anexo;

    private Boolean atendidoFreemium;

    private Boolean atendido;

    private Boolean optanteSimples;

    private Boolean aceitaMEI;

    private String categoria;

    @NotNull
    private ClasseCnaeDTO classe;

    private Set<SegmentoCnaeDTO> segmentoCnaes = new HashSet<>();

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

    public Boolean getAceitaMEI() {
        return aceitaMEI;
    }

    public void setAceitaMEI(Boolean aceitaMEI) {
        this.aceitaMEI = aceitaMEI;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ClasseCnaeDTO getClasse() {
        return classe;
    }

    public void setClasse(ClasseCnaeDTO classe) {
        this.classe = classe;
    }

    public Set<SegmentoCnaeDTO> getSegmentoCnaes() {
        return segmentoCnaes;
    }

    public void setSegmentoCnaes(Set<SegmentoCnaeDTO> segmentoCnaes) {
        this.segmentoCnaes = segmentoCnaes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubclasseCnaeDTO)) {
            return false;
        }

        SubclasseCnaeDTO subclasseCnaeDTO = (SubclasseCnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subclasseCnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubclasseCnaeDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", anexo=" + getAnexo() +
            ", atendidoFreemium='" + getAtendidoFreemium() + "'" +
            ", atendido='" + getAtendido() + "'" +
            ", optanteSimples='" + getOptanteSimples() + "'" +
            ", aceitaMEI='" + getAceitaMEI() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", classe=" + getClasse() +
            ", segmentoCnaes=" + getSegmentoCnaes() +
            "}";
    }
}
