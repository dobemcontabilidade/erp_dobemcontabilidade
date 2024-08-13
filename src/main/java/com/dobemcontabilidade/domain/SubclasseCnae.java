package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubclasseCnae.
 */
@Entity
@Table(name = "subclasse_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubclasseCnae implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "codigo", length = 15, nullable = false)
    private String codigo;

    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "anexo")
    private Integer anexo;

    @Column(name = "atendido_freemium")
    private Boolean atendidoFreemium;

    @Column(name = "atendido")
    private Boolean atendido;

    @Column(name = "optante_simples")
    private Boolean optanteSimples;

    @Column(name = "aceita_mei")
    private Boolean aceitaMEI;

    @Column(name = "categoria")
    private String categoria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subclasse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subclasse" }, allowSetters = true)
    private Set<ObservacaoCnae> observacaoCnaes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cnae")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cnae", "empresa" }, allowSetters = true)
    private Set<AtividadeEmpresa> atividadeEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "subclasseCnaes", "grupo" }, allowSetters = true)
    private ClasseCnae classe;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subclasseCnaes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subclasseCnaes", "ramo", "empresas", "empresaModelos" }, allowSetters = true)
    private Set<SegmentoCnae> segmentoCnaes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubclasseCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public SubclasseCnae codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public SubclasseCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAnexo() {
        return this.anexo;
    }

    public SubclasseCnae anexo(Integer anexo) {
        this.setAnexo(anexo);
        return this;
    }

    public void setAnexo(Integer anexo) {
        this.anexo = anexo;
    }

    public Boolean getAtendidoFreemium() {
        return this.atendidoFreemium;
    }

    public SubclasseCnae atendidoFreemium(Boolean atendidoFreemium) {
        this.setAtendidoFreemium(atendidoFreemium);
        return this;
    }

    public void setAtendidoFreemium(Boolean atendidoFreemium) {
        this.atendidoFreemium = atendidoFreemium;
    }

    public Boolean getAtendido() {
        return this.atendido;
    }

    public SubclasseCnae atendido(Boolean atendido) {
        this.setAtendido(atendido);
        return this;
    }

    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    public Boolean getOptanteSimples() {
        return this.optanteSimples;
    }

    public SubclasseCnae optanteSimples(Boolean optanteSimples) {
        this.setOptanteSimples(optanteSimples);
        return this;
    }

    public void setOptanteSimples(Boolean optanteSimples) {
        this.optanteSimples = optanteSimples;
    }

    public Boolean getAceitaMEI() {
        return this.aceitaMEI;
    }

    public SubclasseCnae aceitaMEI(Boolean aceitaMEI) {
        this.setAceitaMEI(aceitaMEI);
        return this;
    }

    public void setAceitaMEI(Boolean aceitaMEI) {
        this.aceitaMEI = aceitaMEI;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public SubclasseCnae categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<ObservacaoCnae> getObservacaoCnaes() {
        return this.observacaoCnaes;
    }

    public void setObservacaoCnaes(Set<ObservacaoCnae> observacaoCnaes) {
        if (this.observacaoCnaes != null) {
            this.observacaoCnaes.forEach(i -> i.setSubclasse(null));
        }
        if (observacaoCnaes != null) {
            observacaoCnaes.forEach(i -> i.setSubclasse(this));
        }
        this.observacaoCnaes = observacaoCnaes;
    }

    public SubclasseCnae observacaoCnaes(Set<ObservacaoCnae> observacaoCnaes) {
        this.setObservacaoCnaes(observacaoCnaes);
        return this;
    }

    public SubclasseCnae addObservacaoCnae(ObservacaoCnae observacaoCnae) {
        this.observacaoCnaes.add(observacaoCnae);
        observacaoCnae.setSubclasse(this);
        return this;
    }

    public SubclasseCnae removeObservacaoCnae(ObservacaoCnae observacaoCnae) {
        this.observacaoCnaes.remove(observacaoCnae);
        observacaoCnae.setSubclasse(null);
        return this;
    }

    public Set<AtividadeEmpresa> getAtividadeEmpresas() {
        return this.atividadeEmpresas;
    }

    public void setAtividadeEmpresas(Set<AtividadeEmpresa> atividadeEmpresas) {
        if (this.atividadeEmpresas != null) {
            this.atividadeEmpresas.forEach(i -> i.setCnae(null));
        }
        if (atividadeEmpresas != null) {
            atividadeEmpresas.forEach(i -> i.setCnae(this));
        }
        this.atividadeEmpresas = atividadeEmpresas;
    }

    public SubclasseCnae atividadeEmpresas(Set<AtividadeEmpresa> atividadeEmpresas) {
        this.setAtividadeEmpresas(atividadeEmpresas);
        return this;
    }

    public SubclasseCnae addAtividadeEmpresa(AtividadeEmpresa atividadeEmpresa) {
        this.atividadeEmpresas.add(atividadeEmpresa);
        atividadeEmpresa.setCnae(this);
        return this;
    }

    public SubclasseCnae removeAtividadeEmpresa(AtividadeEmpresa atividadeEmpresa) {
        this.atividadeEmpresas.remove(atividadeEmpresa);
        atividadeEmpresa.setCnae(null);
        return this;
    }

    public ClasseCnae getClasse() {
        return this.classe;
    }

    public void setClasse(ClasseCnae classeCnae) {
        this.classe = classeCnae;
    }

    public SubclasseCnae classe(ClasseCnae classeCnae) {
        this.setClasse(classeCnae);
        return this;
    }

    public Set<SegmentoCnae> getSegmentoCnaes() {
        return this.segmentoCnaes;
    }

    public void setSegmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        if (this.segmentoCnaes != null) {
            this.segmentoCnaes.forEach(i -> i.removeSubclasseCnae(this));
        }
        if (segmentoCnaes != null) {
            segmentoCnaes.forEach(i -> i.addSubclasseCnae(this));
        }
        this.segmentoCnaes = segmentoCnaes;
    }

    public SubclasseCnae segmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        this.setSegmentoCnaes(segmentoCnaes);
        return this;
    }

    public SubclasseCnae addSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.add(segmentoCnae);
        segmentoCnae.getSubclasseCnaes().add(this);
        return this;
    }

    public SubclasseCnae removeSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.remove(segmentoCnae);
        segmentoCnae.getSubclasseCnaes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubclasseCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((SubclasseCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubclasseCnae{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", anexo=" + getAnexo() +
            ", atendidoFreemium='" + getAtendidoFreemium() + "'" +
            ", atendido='" + getAtendido() + "'" +
            ", optanteSimples='" + getOptanteSimples() + "'" +
            ", aceitaMEI='" + getAceitaMEI() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
