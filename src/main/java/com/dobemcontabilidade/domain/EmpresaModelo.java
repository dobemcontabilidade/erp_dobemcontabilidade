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
 * A EmpresaModelo.
 */
@Entity
@Table(name = "empresa_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpresaModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "observacao")
    private String observacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaRecorrenteEmpresaModelos", "empresaModelo", "servicoContabil" }, allowSetters = true)
    private Set<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresaModelo", "imposto" }, allowSetters = true)
    private Set<ImpostoEmpresaModelo> impostoEmpresaModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoEmpresas", "anexoRequerido", "enquadramento", "tributacao", "ramo", "empresa", "empresaModelo" },
        allowSetters = true
    )
    private Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresaModelo", "servicoContabil" }, allowSetters = true)
    private Set<TarefaEmpresaModelo> tarefaEmpresaModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaModelo")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_empresa_modelo__segmento_cnae",
        joinColumns = @JoinColumn(name = "empresa_modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "segmento_cnae_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subclasseCnaes", "ramo", "empresas", "empresaModelos" }, allowSetters = true)
    private Set<SegmentoCnae> segmentoCnaes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "anexoRequeridoEmpresas", "empresaModelos", "calculoPlanoAssinaturas", "adicionalRamos", "valorBaseRamos", "segmentoCnaes",
        },
        allowSetters = true
    )
    private Ramo ramo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "anexoRequeridoEmpresas", "empresaModelos", "adicionalEnquadramentos" }, allowSetters = true)
    private Enquadramento enquadramento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "anexoRequeridoEmpresas", "empresaModelos", "calculoPlanoAssinaturas", "adicionalTributacaos" },
        allowSetters = true
    )
    private Tributacao tributacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "instituicaoEnsinos",
            "agenteIntegracaoEstagios",
            "empresaModelos",
            "fluxoModelos",
            "enderecoPessoas",
            "enderecoEmpresas",
            "estado",
        },
        allowSetters = true
    )
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmpresaModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public EmpresaModelo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public EmpresaModelo observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<ServicoContabilEmpresaModelo> getServicoContabilEmpresaModelos() {
        return this.servicoContabilEmpresaModelos;
    }

    public void setServicoContabilEmpresaModelos(Set<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelos) {
        if (this.servicoContabilEmpresaModelos != null) {
            this.servicoContabilEmpresaModelos.forEach(i -> i.setEmpresaModelo(null));
        }
        if (servicoContabilEmpresaModelos != null) {
            servicoContabilEmpresaModelos.forEach(i -> i.setEmpresaModelo(this));
        }
        this.servicoContabilEmpresaModelos = servicoContabilEmpresaModelos;
    }

    public EmpresaModelo servicoContabilEmpresaModelos(Set<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelos) {
        this.setServicoContabilEmpresaModelos(servicoContabilEmpresaModelos);
        return this;
    }

    public EmpresaModelo addServicoContabilEmpresaModelo(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        this.servicoContabilEmpresaModelos.add(servicoContabilEmpresaModelo);
        servicoContabilEmpresaModelo.setEmpresaModelo(this);
        return this;
    }

    public EmpresaModelo removeServicoContabilEmpresaModelo(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        this.servicoContabilEmpresaModelos.remove(servicoContabilEmpresaModelo);
        servicoContabilEmpresaModelo.setEmpresaModelo(null);
        return this;
    }

    public Set<ImpostoEmpresaModelo> getImpostoEmpresaModelos() {
        return this.impostoEmpresaModelos;
    }

    public void setImpostoEmpresaModelos(Set<ImpostoEmpresaModelo> impostoEmpresaModelos) {
        if (this.impostoEmpresaModelos != null) {
            this.impostoEmpresaModelos.forEach(i -> i.setEmpresaModelo(null));
        }
        if (impostoEmpresaModelos != null) {
            impostoEmpresaModelos.forEach(i -> i.setEmpresaModelo(this));
        }
        this.impostoEmpresaModelos = impostoEmpresaModelos;
    }

    public EmpresaModelo impostoEmpresaModelos(Set<ImpostoEmpresaModelo> impostoEmpresaModelos) {
        this.setImpostoEmpresaModelos(impostoEmpresaModelos);
        return this;
    }

    public EmpresaModelo addImpostoEmpresaModelo(ImpostoEmpresaModelo impostoEmpresaModelo) {
        this.impostoEmpresaModelos.add(impostoEmpresaModelo);
        impostoEmpresaModelo.setEmpresaModelo(this);
        return this;
    }

    public EmpresaModelo removeImpostoEmpresaModelo(ImpostoEmpresaModelo impostoEmpresaModelo) {
        this.impostoEmpresaModelos.remove(impostoEmpresaModelo);
        impostoEmpresaModelo.setEmpresaModelo(null);
        return this;
    }

    public Set<AnexoRequeridoEmpresa> getAnexoRequeridoEmpresas() {
        return this.anexoRequeridoEmpresas;
    }

    public void setAnexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        if (this.anexoRequeridoEmpresas != null) {
            this.anexoRequeridoEmpresas.forEach(i -> i.setEmpresaModelo(null));
        }
        if (anexoRequeridoEmpresas != null) {
            anexoRequeridoEmpresas.forEach(i -> i.setEmpresaModelo(this));
        }
        this.anexoRequeridoEmpresas = anexoRequeridoEmpresas;
    }

    public EmpresaModelo anexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        this.setAnexoRequeridoEmpresas(anexoRequeridoEmpresas);
        return this;
    }

    public EmpresaModelo addAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.add(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setEmpresaModelo(this);
        return this;
    }

    public EmpresaModelo removeAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.remove(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setEmpresaModelo(null);
        return this;
    }

    public Set<TarefaEmpresaModelo> getTarefaEmpresaModelos() {
        return this.tarefaEmpresaModelos;
    }

    public void setTarefaEmpresaModelos(Set<TarefaEmpresaModelo> tarefaEmpresaModelos) {
        if (this.tarefaEmpresaModelos != null) {
            this.tarefaEmpresaModelos.forEach(i -> i.setEmpresaModelo(null));
        }
        if (tarefaEmpresaModelos != null) {
            tarefaEmpresaModelos.forEach(i -> i.setEmpresaModelo(this));
        }
        this.tarefaEmpresaModelos = tarefaEmpresaModelos;
    }

    public EmpresaModelo tarefaEmpresaModelos(Set<TarefaEmpresaModelo> tarefaEmpresaModelos) {
        this.setTarefaEmpresaModelos(tarefaEmpresaModelos);
        return this;
    }

    public EmpresaModelo addTarefaEmpresaModelo(TarefaEmpresaModelo tarefaEmpresaModelo) {
        this.tarefaEmpresaModelos.add(tarefaEmpresaModelo);
        tarefaEmpresaModelo.setEmpresaModelo(this);
        return this;
    }

    public EmpresaModelo removeTarefaEmpresaModelo(TarefaEmpresaModelo tarefaEmpresaModelo) {
        this.tarefaEmpresaModelos.remove(tarefaEmpresaModelo);
        tarefaEmpresaModelo.setEmpresaModelo(null);
        return this;
    }

    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        if (this.empresas != null) {
            this.empresas.forEach(i -> i.setEmpresaModelo(null));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.setEmpresaModelo(this));
        }
        this.empresas = empresas;
    }

    public EmpresaModelo empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public EmpresaModelo addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setEmpresaModelo(this);
        return this;
    }

    public EmpresaModelo removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setEmpresaModelo(null);
        return this;
    }

    public Set<SegmentoCnae> getSegmentoCnaes() {
        return this.segmentoCnaes;
    }

    public void setSegmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        this.segmentoCnaes = segmentoCnaes;
    }

    public EmpresaModelo segmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        this.setSegmentoCnaes(segmentoCnaes);
        return this;
    }

    public EmpresaModelo addSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.add(segmentoCnae);
        return this;
    }

    public EmpresaModelo removeSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.remove(segmentoCnae);
        return this;
    }

    public Ramo getRamo() {
        return this.ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public EmpresaModelo ramo(Ramo ramo) {
        this.setRamo(ramo);
        return this;
    }

    public Enquadramento getEnquadramento() {
        return this.enquadramento;
    }

    public void setEnquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
    }

    public EmpresaModelo enquadramento(Enquadramento enquadramento) {
        this.setEnquadramento(enquadramento);
        return this;
    }

    public Tributacao getTributacao() {
        return this.tributacao;
    }

    public void setTributacao(Tributacao tributacao) {
        this.tributacao = tributacao;
    }

    public EmpresaModelo tributacao(Tributacao tributacao) {
        this.setTributacao(tributacao);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public EmpresaModelo cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpresaModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((EmpresaModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaModelo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
