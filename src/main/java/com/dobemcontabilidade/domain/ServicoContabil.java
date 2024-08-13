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
 * A ServicoContabil.
 */
@Entity
@Table(name = "servico_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Float valor;

    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "dias_execucao")
    private Integer diasExecucao;

    @Column(name = "gera_multa")
    private Boolean geraMulta;

    @Column(name = "periodo_execucao")
    private Integer periodoExecucao;

    @Column(name = "dia_legal")
    private Integer diaLegal;

    @Column(name = "mes_legal")
    private Integer mesLegal;

    @Column(name = "valor_ref_multa")
    private Double valorRefMulta;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaRecorrenteEmpresaModelos", "empresaModelo", "servicoContabil" }, allowSetters = true)
    private Set<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etapaFluxoModelo", "servicoContabil" }, allowSetters = true)
    private Set<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabil", "etapaFluxoExecucao" }, allowSetters = true)
    private Set<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabil", "anexoRequerido" }, allowSetters = true)
    private Set<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaOrdemServicos", "servicoContabil", "ordemServico" }, allowSetters = true)
    private Set<ServicoContabilOrdemServico> servicoContabilOrdemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoServicoContabilEmpresas", "tarefaRecorrentes", "servicoContabil", "assinaturaEmpresa" },
        allowSetters = true
    )
    private Set<ServicoContabilAssinaturaEmpresa> servicoContabilAssinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresaModelo", "servicoContabil" }, allowSetters = true)
    private Set<TarefaEmpresaModelo> tarefaEmpresaModelos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaContabilAssinaturaEmpresas", "servicoContabils", "areaContabilContadors" }, allowSetters = true)
    private AreaContabil areaContabil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "servicoContabils", "impostos", "tarefas" }, allowSetters = true)
    private Esfera esfera;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServicoContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public ServicoContabil nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return this.valor;
    }

    public ServicoContabil valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ServicoContabil descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiasExecucao() {
        return this.diasExecucao;
    }

    public ServicoContabil diasExecucao(Integer diasExecucao) {
        this.setDiasExecucao(diasExecucao);
        return this;
    }

    public void setDiasExecucao(Integer diasExecucao) {
        this.diasExecucao = diasExecucao;
    }

    public Boolean getGeraMulta() {
        return this.geraMulta;
    }

    public ServicoContabil geraMulta(Boolean geraMulta) {
        this.setGeraMulta(geraMulta);
        return this;
    }

    public void setGeraMulta(Boolean geraMulta) {
        this.geraMulta = geraMulta;
    }

    public Integer getPeriodoExecucao() {
        return this.periodoExecucao;
    }

    public ServicoContabil periodoExecucao(Integer periodoExecucao) {
        this.setPeriodoExecucao(periodoExecucao);
        return this;
    }

    public void setPeriodoExecucao(Integer periodoExecucao) {
        this.periodoExecucao = periodoExecucao;
    }

    public Integer getDiaLegal() {
        return this.diaLegal;
    }

    public ServicoContabil diaLegal(Integer diaLegal) {
        this.setDiaLegal(diaLegal);
        return this;
    }

    public void setDiaLegal(Integer diaLegal) {
        this.diaLegal = diaLegal;
    }

    public Integer getMesLegal() {
        return this.mesLegal;
    }

    public ServicoContabil mesLegal(Integer mesLegal) {
        this.setMesLegal(mesLegal);
        return this;
    }

    public void setMesLegal(Integer mesLegal) {
        this.mesLegal = mesLegal;
    }

    public Double getValorRefMulta() {
        return this.valorRefMulta;
    }

    public ServicoContabil valorRefMulta(Double valorRefMulta) {
        this.setValorRefMulta(valorRefMulta);
        return this;
    }

    public void setValorRefMulta(Double valorRefMulta) {
        this.valorRefMulta = valorRefMulta;
    }

    public Set<ServicoContabilEmpresaModelo> getServicoContabilEmpresaModelos() {
        return this.servicoContabilEmpresaModelos;
    }

    public void setServicoContabilEmpresaModelos(Set<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelos) {
        if (this.servicoContabilEmpresaModelos != null) {
            this.servicoContabilEmpresaModelos.forEach(i -> i.setServicoContabil(null));
        }
        if (servicoContabilEmpresaModelos != null) {
            servicoContabilEmpresaModelos.forEach(i -> i.setServicoContabil(this));
        }
        this.servicoContabilEmpresaModelos = servicoContabilEmpresaModelos;
    }

    public ServicoContabil servicoContabilEmpresaModelos(Set<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelos) {
        this.setServicoContabilEmpresaModelos(servicoContabilEmpresaModelos);
        return this;
    }

    public ServicoContabil addServicoContabilEmpresaModelo(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        this.servicoContabilEmpresaModelos.add(servicoContabilEmpresaModelo);
        servicoContabilEmpresaModelo.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeServicoContabilEmpresaModelo(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        this.servicoContabilEmpresaModelos.remove(servicoContabilEmpresaModelo);
        servicoContabilEmpresaModelo.setServicoContabil(null);
        return this;
    }

    public Set<ServicoContabilEtapaFluxoModelo> getServicoContabilEtapaFluxoModelos() {
        return this.servicoContabilEtapaFluxoModelos;
    }

    public void setServicoContabilEtapaFluxoModelos(Set<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelos) {
        if (this.servicoContabilEtapaFluxoModelos != null) {
            this.servicoContabilEtapaFluxoModelos.forEach(i -> i.setServicoContabil(null));
        }
        if (servicoContabilEtapaFluxoModelos != null) {
            servicoContabilEtapaFluxoModelos.forEach(i -> i.setServicoContabil(this));
        }
        this.servicoContabilEtapaFluxoModelos = servicoContabilEtapaFluxoModelos;
    }

    public ServicoContabil servicoContabilEtapaFluxoModelos(Set<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelos) {
        this.setServicoContabilEtapaFluxoModelos(servicoContabilEtapaFluxoModelos);
        return this;
    }

    public ServicoContabil addServicoContabilEtapaFluxoModelo(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        this.servicoContabilEtapaFluxoModelos.add(servicoContabilEtapaFluxoModelo);
        servicoContabilEtapaFluxoModelo.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeServicoContabilEtapaFluxoModelo(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        this.servicoContabilEtapaFluxoModelos.remove(servicoContabilEtapaFluxoModelo);
        servicoContabilEtapaFluxoModelo.setServicoContabil(null);
        return this;
    }

    public Set<ServicoContabilEtapaFluxoExecucao> getServicoContabilEtapaFluxoExecucaos() {
        return this.servicoContabilEtapaFluxoExecucaos;
    }

    public void setServicoContabilEtapaFluxoExecucaos(Set<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucaos) {
        if (this.servicoContabilEtapaFluxoExecucaos != null) {
            this.servicoContabilEtapaFluxoExecucaos.forEach(i -> i.setServicoContabil(null));
        }
        if (servicoContabilEtapaFluxoExecucaos != null) {
            servicoContabilEtapaFluxoExecucaos.forEach(i -> i.setServicoContabil(this));
        }
        this.servicoContabilEtapaFluxoExecucaos = servicoContabilEtapaFluxoExecucaos;
    }

    public ServicoContabil servicoContabilEtapaFluxoExecucaos(Set<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucaos) {
        this.setServicoContabilEtapaFluxoExecucaos(servicoContabilEtapaFluxoExecucaos);
        return this;
    }

    public ServicoContabil addServicoContabilEtapaFluxoExecucao(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        this.servicoContabilEtapaFluxoExecucaos.add(servicoContabilEtapaFluxoExecucao);
        servicoContabilEtapaFluxoExecucao.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeServicoContabilEtapaFluxoExecucao(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        this.servicoContabilEtapaFluxoExecucaos.remove(servicoContabilEtapaFluxoExecucao);
        servicoContabilEtapaFluxoExecucao.setServicoContabil(null);
        return this;
    }

    public Set<AnexoRequeridoServicoContabil> getAnexoRequeridoServicoContabils() {
        return this.anexoRequeridoServicoContabils;
    }

    public void setAnexoRequeridoServicoContabils(Set<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabils) {
        if (this.anexoRequeridoServicoContabils != null) {
            this.anexoRequeridoServicoContabils.forEach(i -> i.setServicoContabil(null));
        }
        if (anexoRequeridoServicoContabils != null) {
            anexoRequeridoServicoContabils.forEach(i -> i.setServicoContabil(this));
        }
        this.anexoRequeridoServicoContabils = anexoRequeridoServicoContabils;
    }

    public ServicoContabil anexoRequeridoServicoContabils(Set<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabils) {
        this.setAnexoRequeridoServicoContabils(anexoRequeridoServicoContabils);
        return this;
    }

    public ServicoContabil addAnexoRequeridoServicoContabil(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        this.anexoRequeridoServicoContabils.add(anexoRequeridoServicoContabil);
        anexoRequeridoServicoContabil.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeAnexoRequeridoServicoContabil(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        this.anexoRequeridoServicoContabils.remove(anexoRequeridoServicoContabil);
        anexoRequeridoServicoContabil.setServicoContabil(null);
        return this;
    }

    public Set<ServicoContabilOrdemServico> getServicoContabilOrdemServicos() {
        return this.servicoContabilOrdemServicos;
    }

    public void setServicoContabilOrdemServicos(Set<ServicoContabilOrdemServico> servicoContabilOrdemServicos) {
        if (this.servicoContabilOrdemServicos != null) {
            this.servicoContabilOrdemServicos.forEach(i -> i.setServicoContabil(null));
        }
        if (servicoContabilOrdemServicos != null) {
            servicoContabilOrdemServicos.forEach(i -> i.setServicoContabil(this));
        }
        this.servicoContabilOrdemServicos = servicoContabilOrdemServicos;
    }

    public ServicoContabil servicoContabilOrdemServicos(Set<ServicoContabilOrdemServico> servicoContabilOrdemServicos) {
        this.setServicoContabilOrdemServicos(servicoContabilOrdemServicos);
        return this;
    }

    public ServicoContabil addServicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        this.servicoContabilOrdemServicos.add(servicoContabilOrdemServico);
        servicoContabilOrdemServico.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeServicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        this.servicoContabilOrdemServicos.remove(servicoContabilOrdemServico);
        servicoContabilOrdemServico.setServicoContabil(null);
        return this;
    }

    public Set<ServicoContabilAssinaturaEmpresa> getServicoContabilAssinaturaEmpresas() {
        return this.servicoContabilAssinaturaEmpresas;
    }

    public void setServicoContabilAssinaturaEmpresas(Set<ServicoContabilAssinaturaEmpresa> servicoContabilAssinaturaEmpresas) {
        if (this.servicoContabilAssinaturaEmpresas != null) {
            this.servicoContabilAssinaturaEmpresas.forEach(i -> i.setServicoContabil(null));
        }
        if (servicoContabilAssinaturaEmpresas != null) {
            servicoContabilAssinaturaEmpresas.forEach(i -> i.setServicoContabil(this));
        }
        this.servicoContabilAssinaturaEmpresas = servicoContabilAssinaturaEmpresas;
    }

    public ServicoContabil servicoContabilAssinaturaEmpresas(Set<ServicoContabilAssinaturaEmpresa> servicoContabilAssinaturaEmpresas) {
        this.setServicoContabilAssinaturaEmpresas(servicoContabilAssinaturaEmpresas);
        return this;
    }

    public ServicoContabil addServicoContabilAssinaturaEmpresa(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        this.servicoContabilAssinaturaEmpresas.add(servicoContabilAssinaturaEmpresa);
        servicoContabilAssinaturaEmpresa.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeServicoContabilAssinaturaEmpresa(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        this.servicoContabilAssinaturaEmpresas.remove(servicoContabilAssinaturaEmpresa);
        servicoContabilAssinaturaEmpresa.setServicoContabil(null);
        return this;
    }

    public Set<TarefaEmpresaModelo> getTarefaEmpresaModelos() {
        return this.tarefaEmpresaModelos;
    }

    public void setTarefaEmpresaModelos(Set<TarefaEmpresaModelo> tarefaEmpresaModelos) {
        if (this.tarefaEmpresaModelos != null) {
            this.tarefaEmpresaModelos.forEach(i -> i.setServicoContabil(null));
        }
        if (tarefaEmpresaModelos != null) {
            tarefaEmpresaModelos.forEach(i -> i.setServicoContabil(this));
        }
        this.tarefaEmpresaModelos = tarefaEmpresaModelos;
    }

    public ServicoContabil tarefaEmpresaModelos(Set<TarefaEmpresaModelo> tarefaEmpresaModelos) {
        this.setTarefaEmpresaModelos(tarefaEmpresaModelos);
        return this;
    }

    public ServicoContabil addTarefaEmpresaModelo(TarefaEmpresaModelo tarefaEmpresaModelo) {
        this.tarefaEmpresaModelos.add(tarefaEmpresaModelo);
        tarefaEmpresaModelo.setServicoContabil(this);
        return this;
    }

    public ServicoContabil removeTarefaEmpresaModelo(TarefaEmpresaModelo tarefaEmpresaModelo) {
        this.tarefaEmpresaModelos.remove(tarefaEmpresaModelo);
        tarefaEmpresaModelo.setServicoContabil(null);
        return this;
    }

    public AreaContabil getAreaContabil() {
        return this.areaContabil;
    }

    public void setAreaContabil(AreaContabil areaContabil) {
        this.areaContabil = areaContabil;
    }

    public ServicoContabil areaContabil(AreaContabil areaContabil) {
        this.setAreaContabil(areaContabil);
        return this;
    }

    public Esfera getEsfera() {
        return this.esfera;
    }

    public void setEsfera(Esfera esfera) {
        this.esfera = esfera;
    }

    public ServicoContabil esfera(Esfera esfera) {
        this.setEsfera(esfera);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicoContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicoContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoContabil{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", descricao='" + getDescricao() + "'" +
            ", diasExecucao=" + getDiasExecucao() +
            ", geraMulta='" + getGeraMulta() + "'" +
            ", periodoExecucao=" + getPeriodoExecucao() +
            ", diaLegal=" + getDiaLegal() +
            ", mesLegal=" + getMesLegal() +
            ", valorRefMulta=" + getValorRefMulta() +
            "}";
    }
}
