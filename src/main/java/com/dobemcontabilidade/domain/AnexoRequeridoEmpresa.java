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
 * A AnexoRequeridoEmpresa.
 */
@Entity
@Table(name = "anexo_requerido_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequeridoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

    @Lob
    @Column(name = "url_arquivo")
    private String urlArquivo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequeridoEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "anexoRequeridoEmpresa" }, allowSetters = true)
    private Set<AnexoEmpresa> anexoEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "anexoRequeridoPessoas",
            "anexoRequeridoEmpresas",
            "anexoRequeridoServicoContabils",
            "anexoServicoContabilEmpresas",
            "anexoRequeridoTarefaOrdemServicos",
            "anexoRequeridoTarefaRecorrentes",
        },
        allowSetters = true
    )
    private AnexoRequerido anexoRequerido;

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
            "anexoRequeridoEmpresas", "empresaModelos", "calculoPlanoAssinaturas", "adicionalRamos", "valorBaseRamos", "segmentoCnaes",
        },
        allowSetters = true
    )
    private Ramo ramo;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
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
    private EmpresaModelo empresaModelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoRequeridoEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public AnexoRequeridoEmpresa obrigatorio(Boolean obrigatorio) {
        this.setObrigatorio(obrigatorio);
        return this;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public String getUrlArquivo() {
        return this.urlArquivo;
    }

    public AnexoRequeridoEmpresa urlArquivo(String urlArquivo) {
        this.setUrlArquivo(urlArquivo);
        return this;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public Set<AnexoEmpresa> getAnexoEmpresas() {
        return this.anexoEmpresas;
    }

    public void setAnexoEmpresas(Set<AnexoEmpresa> anexoEmpresas) {
        if (this.anexoEmpresas != null) {
            this.anexoEmpresas.forEach(i -> i.setAnexoRequeridoEmpresa(null));
        }
        if (anexoEmpresas != null) {
            anexoEmpresas.forEach(i -> i.setAnexoRequeridoEmpresa(this));
        }
        this.anexoEmpresas = anexoEmpresas;
    }

    public AnexoRequeridoEmpresa anexoEmpresas(Set<AnexoEmpresa> anexoEmpresas) {
        this.setAnexoEmpresas(anexoEmpresas);
        return this;
    }

    public AnexoRequeridoEmpresa addAnexoEmpresa(AnexoEmpresa anexoEmpresa) {
        this.anexoEmpresas.add(anexoEmpresa);
        anexoEmpresa.setAnexoRequeridoEmpresa(this);
        return this;
    }

    public AnexoRequeridoEmpresa removeAnexoEmpresa(AnexoEmpresa anexoEmpresa) {
        this.anexoEmpresas.remove(anexoEmpresa);
        anexoEmpresa.setAnexoRequeridoEmpresa(null);
        return this;
    }

    public AnexoRequerido getAnexoRequerido() {
        return this.anexoRequerido;
    }

    public void setAnexoRequerido(AnexoRequerido anexoRequerido) {
        this.anexoRequerido = anexoRequerido;
    }

    public AnexoRequeridoEmpresa anexoRequerido(AnexoRequerido anexoRequerido) {
        this.setAnexoRequerido(anexoRequerido);
        return this;
    }

    public Enquadramento getEnquadramento() {
        return this.enquadramento;
    }

    public void setEnquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
    }

    public AnexoRequeridoEmpresa enquadramento(Enquadramento enquadramento) {
        this.setEnquadramento(enquadramento);
        return this;
    }

    public Tributacao getTributacao() {
        return this.tributacao;
    }

    public void setTributacao(Tributacao tributacao) {
        this.tributacao = tributacao;
    }

    public AnexoRequeridoEmpresa tributacao(Tributacao tributacao) {
        this.setTributacao(tributacao);
        return this;
    }

    public Ramo getRamo() {
        return this.ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public AnexoRequeridoEmpresa ramo(Ramo ramo) {
        this.setRamo(ramo);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public AnexoRequeridoEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public EmpresaModelo getEmpresaModelo() {
        return this.empresaModelo;
    }

    public void setEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelo = empresaModelo;
    }

    public AnexoRequeridoEmpresa empresaModelo(EmpresaModelo empresaModelo) {
        this.setEmpresaModelo(empresaModelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoRequeridoEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoRequeridoEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequeridoEmpresa{" +
            "id=" + getId() +
            ", obrigatorio='" + getObrigatorio() + "'" +
            ", urlArquivo='" + getUrlArquivo() + "'" +
            "}";
    }
}
