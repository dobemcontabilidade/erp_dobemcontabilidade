package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoSegmentoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SegmentoCnae.
 */
@Entity
@Table(name = "segmento_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SegmentoCnae implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;

    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "icon")
    private String icon;

    @Column(name = "imagem")
    private String imagem;

    @Lob
    @Column(name = "tags")
    private String tags;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoSegmentoEnum tipo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_segmento_cnae__subclasse_cnae",
        joinColumns = @JoinColumn(name = "segmento_cnae_id"),
        inverseJoinColumns = @JoinColumn(name = "subclasse_cnae_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "observacaoCnaes", "atividadeEmpresas", "classe", "segmentoCnaes" }, allowSetters = true)
    private Set<SubclasseCnae> subclasseCnaes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "anexoRequeridoEmpresas", "empresaModelos", "calculoPlanoAssinaturas", "adicionalRamos", "valorBaseRamos", "segmentoCnaes",
        },
        allowSetters = true
    )
    private Ramo ramo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "segmentoCnaes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoEmpresas",
            "ordemServicos",
            "anexoRequeridoEmpresas",
            "impostoEmpresas",
            "parcelamentoImpostos",
            "assinaturaEmpresas",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "certificadoDigitals",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "termoAdesaoEmpresas",
            "segmentoCnaes",
            "empresaModelo",
        },
        allowSetters = true
    )
    private Set<Empresa> empresas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "segmentoCnaes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "impostoEmpresaModelos",
            "anexoRequeridoEmpresas",
            "tarefaEmpresaModelos",
            "empresas",
            "segmentoCnaes",
            "ramo",
            "enquadramento",
            "tributacao",
            "cidade",
        },
        allowSetters = true
    )
    private Set<EmpresaModelo> empresaModelos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SegmentoCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public SegmentoCnae nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public SegmentoCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIcon() {
        return this.icon;
    }

    public SegmentoCnae icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImagem() {
        return this.imagem;
    }

    public SegmentoCnae imagem(String imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTags() {
        return this.tags;
    }

    public SegmentoCnae tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public TipoSegmentoEnum getTipo() {
        return this.tipo;
    }

    public SegmentoCnae tipo(TipoSegmentoEnum tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoSegmentoEnum tipo) {
        this.tipo = tipo;
    }

    public Set<SubclasseCnae> getSubclasseCnaes() {
        return this.subclasseCnaes;
    }

    public void setSubclasseCnaes(Set<SubclasseCnae> subclasseCnaes) {
        this.subclasseCnaes = subclasseCnaes;
    }

    public SegmentoCnae subclasseCnaes(Set<SubclasseCnae> subclasseCnaes) {
        this.setSubclasseCnaes(subclasseCnaes);
        return this;
    }

    public SegmentoCnae addSubclasseCnae(SubclasseCnae subclasseCnae) {
        this.subclasseCnaes.add(subclasseCnae);
        return this;
    }

    public SegmentoCnae removeSubclasseCnae(SubclasseCnae subclasseCnae) {
        this.subclasseCnaes.remove(subclasseCnae);
        return this;
    }

    public Ramo getRamo() {
        return this.ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public SegmentoCnae ramo(Ramo ramo) {
        this.setRamo(ramo);
        return this;
    }

    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        if (this.empresas != null) {
            this.empresas.forEach(i -> i.removeSegmentoCnae(this));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.addSegmentoCnae(this));
        }
        this.empresas = empresas;
    }

    public SegmentoCnae empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public SegmentoCnae addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.getSegmentoCnaes().add(this);
        return this;
    }

    public SegmentoCnae removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.getSegmentoCnaes().remove(this);
        return this;
    }

    public Set<EmpresaModelo> getEmpresaModelos() {
        return this.empresaModelos;
    }

    public void setEmpresaModelos(Set<EmpresaModelo> empresaModelos) {
        if (this.empresaModelos != null) {
            this.empresaModelos.forEach(i -> i.removeSegmentoCnae(this));
        }
        if (empresaModelos != null) {
            empresaModelos.forEach(i -> i.addSegmentoCnae(this));
        }
        this.empresaModelos = empresaModelos;
    }

    public SegmentoCnae empresaModelos(Set<EmpresaModelo> empresaModelos) {
        this.setEmpresaModelos(empresaModelos);
        return this;
    }

    public SegmentoCnae addEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.add(empresaModelo);
        empresaModelo.getSegmentoCnaes().add(this);
        return this;
    }

    public SegmentoCnae removeEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.remove(empresaModelo);
        empresaModelo.getSegmentoCnaes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegmentoCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((SegmentoCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SegmentoCnae{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", icon='" + getIcon() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", tags='" + getTags() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
