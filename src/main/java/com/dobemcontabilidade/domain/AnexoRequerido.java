package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoAnexoRequeridoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoRequerido.
 */
@Entity
@Table(name = "anexo_requerido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequerido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoAnexoRequeridoEnum tipo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequerido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoPessoa", "anexoRequerido" }, allowSetters = true)
    private Set<AnexoRequeridoPessoa> anexoRequeridoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequerido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoEmpresas", "anexoRequerido", "enquadramento", "tributacao", "ramo", "empresa", "empresaModelo" },
        allowSetters = true
    )
    private Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequerido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabil", "anexoRequerido" }, allowSetters = true)
    private Set<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequerido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequerido", "servicoContabilAssinaturaEmpresa" }, allowSetters = true)
    private Set<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequerido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequerido", "tarefaOrdemServico" }, allowSetters = true)
    private Set<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoRequerido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequerido", "tarefaRecorrente" }, allowSetters = true)
    private Set<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoRequerido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public AnexoRequerido nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoAnexoRequeridoEnum getTipo() {
        return this.tipo;
    }

    public AnexoRequerido tipo(TipoAnexoRequeridoEnum tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoAnexoRequeridoEnum tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AnexoRequerido descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<AnexoRequeridoPessoa> getAnexoRequeridoPessoas() {
        return this.anexoRequeridoPessoas;
    }

    public void setAnexoRequeridoPessoas(Set<AnexoRequeridoPessoa> anexoRequeridoPessoas) {
        if (this.anexoRequeridoPessoas != null) {
            this.anexoRequeridoPessoas.forEach(i -> i.setAnexoRequerido(null));
        }
        if (anexoRequeridoPessoas != null) {
            anexoRequeridoPessoas.forEach(i -> i.setAnexoRequerido(this));
        }
        this.anexoRequeridoPessoas = anexoRequeridoPessoas;
    }

    public AnexoRequerido anexoRequeridoPessoas(Set<AnexoRequeridoPessoa> anexoRequeridoPessoas) {
        this.setAnexoRequeridoPessoas(anexoRequeridoPessoas);
        return this;
    }

    public AnexoRequerido addAnexoRequeridoPessoa(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        this.anexoRequeridoPessoas.add(anexoRequeridoPessoa);
        anexoRequeridoPessoa.setAnexoRequerido(this);
        return this;
    }

    public AnexoRequerido removeAnexoRequeridoPessoa(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        this.anexoRequeridoPessoas.remove(anexoRequeridoPessoa);
        anexoRequeridoPessoa.setAnexoRequerido(null);
        return this;
    }

    public Set<AnexoRequeridoEmpresa> getAnexoRequeridoEmpresas() {
        return this.anexoRequeridoEmpresas;
    }

    public void setAnexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        if (this.anexoRequeridoEmpresas != null) {
            this.anexoRequeridoEmpresas.forEach(i -> i.setAnexoRequerido(null));
        }
        if (anexoRequeridoEmpresas != null) {
            anexoRequeridoEmpresas.forEach(i -> i.setAnexoRequerido(this));
        }
        this.anexoRequeridoEmpresas = anexoRequeridoEmpresas;
    }

    public AnexoRequerido anexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        this.setAnexoRequeridoEmpresas(anexoRequeridoEmpresas);
        return this;
    }

    public AnexoRequerido addAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.add(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setAnexoRequerido(this);
        return this;
    }

    public AnexoRequerido removeAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.remove(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setAnexoRequerido(null);
        return this;
    }

    public Set<AnexoRequeridoServicoContabil> getAnexoRequeridoServicoContabils() {
        return this.anexoRequeridoServicoContabils;
    }

    public void setAnexoRequeridoServicoContabils(Set<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabils) {
        if (this.anexoRequeridoServicoContabils != null) {
            this.anexoRequeridoServicoContabils.forEach(i -> i.setAnexoRequerido(null));
        }
        if (anexoRequeridoServicoContabils != null) {
            anexoRequeridoServicoContabils.forEach(i -> i.setAnexoRequerido(this));
        }
        this.anexoRequeridoServicoContabils = anexoRequeridoServicoContabils;
    }

    public AnexoRequerido anexoRequeridoServicoContabils(Set<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabils) {
        this.setAnexoRequeridoServicoContabils(anexoRequeridoServicoContabils);
        return this;
    }

    public AnexoRequerido addAnexoRequeridoServicoContabil(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        this.anexoRequeridoServicoContabils.add(anexoRequeridoServicoContabil);
        anexoRequeridoServicoContabil.setAnexoRequerido(this);
        return this;
    }

    public AnexoRequerido removeAnexoRequeridoServicoContabil(AnexoRequeridoServicoContabil anexoRequeridoServicoContabil) {
        this.anexoRequeridoServicoContabils.remove(anexoRequeridoServicoContabil);
        anexoRequeridoServicoContabil.setAnexoRequerido(null);
        return this;
    }

    public Set<AnexoServicoContabilEmpresa> getAnexoServicoContabilEmpresas() {
        return this.anexoServicoContabilEmpresas;
    }

    public void setAnexoServicoContabilEmpresas(Set<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresas) {
        if (this.anexoServicoContabilEmpresas != null) {
            this.anexoServicoContabilEmpresas.forEach(i -> i.setAnexoRequerido(null));
        }
        if (anexoServicoContabilEmpresas != null) {
            anexoServicoContabilEmpresas.forEach(i -> i.setAnexoRequerido(this));
        }
        this.anexoServicoContabilEmpresas = anexoServicoContabilEmpresas;
    }

    public AnexoRequerido anexoServicoContabilEmpresas(Set<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresas) {
        this.setAnexoServicoContabilEmpresas(anexoServicoContabilEmpresas);
        return this;
    }

    public AnexoRequerido addAnexoServicoContabilEmpresa(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        this.anexoServicoContabilEmpresas.add(anexoServicoContabilEmpresa);
        anexoServicoContabilEmpresa.setAnexoRequerido(this);
        return this;
    }

    public AnexoRequerido removeAnexoServicoContabilEmpresa(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        this.anexoServicoContabilEmpresas.remove(anexoServicoContabilEmpresa);
        anexoServicoContabilEmpresa.setAnexoRequerido(null);
        return this;
    }

    public Set<AnexoRequeridoTarefaOrdemServico> getAnexoRequeridoTarefaOrdemServicos() {
        return this.anexoRequeridoTarefaOrdemServicos;
    }

    public void setAnexoRequeridoTarefaOrdemServicos(Set<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServicos) {
        if (this.anexoRequeridoTarefaOrdemServicos != null) {
            this.anexoRequeridoTarefaOrdemServicos.forEach(i -> i.setAnexoRequerido(null));
        }
        if (anexoRequeridoTarefaOrdemServicos != null) {
            anexoRequeridoTarefaOrdemServicos.forEach(i -> i.setAnexoRequerido(this));
        }
        this.anexoRequeridoTarefaOrdemServicos = anexoRequeridoTarefaOrdemServicos;
    }

    public AnexoRequerido anexoRequeridoTarefaOrdemServicos(Set<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServicos) {
        this.setAnexoRequeridoTarefaOrdemServicos(anexoRequeridoTarefaOrdemServicos);
        return this;
    }

    public AnexoRequerido addAnexoRequeridoTarefaOrdemServico(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        this.anexoRequeridoTarefaOrdemServicos.add(anexoRequeridoTarefaOrdemServico);
        anexoRequeridoTarefaOrdemServico.setAnexoRequerido(this);
        return this;
    }

    public AnexoRequerido removeAnexoRequeridoTarefaOrdemServico(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        this.anexoRequeridoTarefaOrdemServicos.remove(anexoRequeridoTarefaOrdemServico);
        anexoRequeridoTarefaOrdemServico.setAnexoRequerido(null);
        return this;
    }

    public Set<AnexoRequeridoTarefaRecorrente> getAnexoRequeridoTarefaRecorrentes() {
        return this.anexoRequeridoTarefaRecorrentes;
    }

    public void setAnexoRequeridoTarefaRecorrentes(Set<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrentes) {
        if (this.anexoRequeridoTarefaRecorrentes != null) {
            this.anexoRequeridoTarefaRecorrentes.forEach(i -> i.setAnexoRequerido(null));
        }
        if (anexoRequeridoTarefaRecorrentes != null) {
            anexoRequeridoTarefaRecorrentes.forEach(i -> i.setAnexoRequerido(this));
        }
        this.anexoRequeridoTarefaRecorrentes = anexoRequeridoTarefaRecorrentes;
    }

    public AnexoRequerido anexoRequeridoTarefaRecorrentes(Set<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrentes) {
        this.setAnexoRequeridoTarefaRecorrentes(anexoRequeridoTarefaRecorrentes);
        return this;
    }

    public AnexoRequerido addAnexoRequeridoTarefaRecorrente(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        this.anexoRequeridoTarefaRecorrentes.add(anexoRequeridoTarefaRecorrente);
        anexoRequeridoTarefaRecorrente.setAnexoRequerido(this);
        return this;
    }

    public AnexoRequerido removeAnexoRequeridoTarefaRecorrente(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        this.anexoRequeridoTarefaRecorrentes.remove(anexoRequeridoTarefaRecorrente);
        anexoRequeridoTarefaRecorrente.setAnexoRequerido(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoRequerido)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoRequerido) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequerido{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
