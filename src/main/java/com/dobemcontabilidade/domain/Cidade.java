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
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "contratacao")
    private Boolean contratacao;

    @Column(name = "abertura")
    private Boolean abertura;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contratoFuncionarios", "cidade" }, allowSetters = true)
    private Set<InstituicaoEnsino> instituicaoEnsinos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contratoFuncionarios", "cidade" }, allowSetters = true)
    private Set<AgenteIntegracaoEstagio> agenteIntegracaoEstagios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ordemServicos", "etapaFluxoModelos", "cidade" }, allowSetters = true)
    private Set<FluxoModelo> fluxoModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "cidade" }, allowSetters = true)
    private Set<EnderecoPessoa> enderecoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "cidade" }, allowSetters = true)
    private Set<EnderecoEmpresa> enderecoEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cidades" }, allowSetters = true)
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cidade nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getContratacao() {
        return this.contratacao;
    }

    public Cidade contratacao(Boolean contratacao) {
        this.setContratacao(contratacao);
        return this;
    }

    public void setContratacao(Boolean contratacao) {
        this.contratacao = contratacao;
    }

    public Boolean getAbertura() {
        return this.abertura;
    }

    public Cidade abertura(Boolean abertura) {
        this.setAbertura(abertura);
        return this;
    }

    public void setAbertura(Boolean abertura) {
        this.abertura = abertura;
    }

    public Set<InstituicaoEnsino> getInstituicaoEnsinos() {
        return this.instituicaoEnsinos;
    }

    public void setInstituicaoEnsinos(Set<InstituicaoEnsino> instituicaoEnsinos) {
        if (this.instituicaoEnsinos != null) {
            this.instituicaoEnsinos.forEach(i -> i.setCidade(null));
        }
        if (instituicaoEnsinos != null) {
            instituicaoEnsinos.forEach(i -> i.setCidade(this));
        }
        this.instituicaoEnsinos = instituicaoEnsinos;
    }

    public Cidade instituicaoEnsinos(Set<InstituicaoEnsino> instituicaoEnsinos) {
        this.setInstituicaoEnsinos(instituicaoEnsinos);
        return this;
    }

    public Cidade addInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsinos.add(instituicaoEnsino);
        instituicaoEnsino.setCidade(this);
        return this;
    }

    public Cidade removeInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsinos.remove(instituicaoEnsino);
        instituicaoEnsino.setCidade(null);
        return this;
    }

    public Set<AgenteIntegracaoEstagio> getAgenteIntegracaoEstagios() {
        return this.agenteIntegracaoEstagios;
    }

    public void setAgenteIntegracaoEstagios(Set<AgenteIntegracaoEstagio> agenteIntegracaoEstagios) {
        if (this.agenteIntegracaoEstagios != null) {
            this.agenteIntegracaoEstagios.forEach(i -> i.setCidade(null));
        }
        if (agenteIntegracaoEstagios != null) {
            agenteIntegracaoEstagios.forEach(i -> i.setCidade(this));
        }
        this.agenteIntegracaoEstagios = agenteIntegracaoEstagios;
    }

    public Cidade agenteIntegracaoEstagios(Set<AgenteIntegracaoEstagio> agenteIntegracaoEstagios) {
        this.setAgenteIntegracaoEstagios(agenteIntegracaoEstagios);
        return this;
    }

    public Cidade addAgenteIntegracaoEstagio(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        this.agenteIntegracaoEstagios.add(agenteIntegracaoEstagio);
        agenteIntegracaoEstagio.setCidade(this);
        return this;
    }

    public Cidade removeAgenteIntegracaoEstagio(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        this.agenteIntegracaoEstagios.remove(agenteIntegracaoEstagio);
        agenteIntegracaoEstagio.setCidade(null);
        return this;
    }

    public Set<EmpresaModelo> getEmpresaModelos() {
        return this.empresaModelos;
    }

    public void setEmpresaModelos(Set<EmpresaModelo> empresaModelos) {
        if (this.empresaModelos != null) {
            this.empresaModelos.forEach(i -> i.setCidade(null));
        }
        if (empresaModelos != null) {
            empresaModelos.forEach(i -> i.setCidade(this));
        }
        this.empresaModelos = empresaModelos;
    }

    public Cidade empresaModelos(Set<EmpresaModelo> empresaModelos) {
        this.setEmpresaModelos(empresaModelos);
        return this;
    }

    public Cidade addEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.add(empresaModelo);
        empresaModelo.setCidade(this);
        return this;
    }

    public Cidade removeEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.remove(empresaModelo);
        empresaModelo.setCidade(null);
        return this;
    }

    public Set<FluxoModelo> getFluxoModelos() {
        return this.fluxoModelos;
    }

    public void setFluxoModelos(Set<FluxoModelo> fluxoModelos) {
        if (this.fluxoModelos != null) {
            this.fluxoModelos.forEach(i -> i.setCidade(null));
        }
        if (fluxoModelos != null) {
            fluxoModelos.forEach(i -> i.setCidade(this));
        }
        this.fluxoModelos = fluxoModelos;
    }

    public Cidade fluxoModelos(Set<FluxoModelo> fluxoModelos) {
        this.setFluxoModelos(fluxoModelos);
        return this;
    }

    public Cidade addFluxoModelo(FluxoModelo fluxoModelo) {
        this.fluxoModelos.add(fluxoModelo);
        fluxoModelo.setCidade(this);
        return this;
    }

    public Cidade removeFluxoModelo(FluxoModelo fluxoModelo) {
        this.fluxoModelos.remove(fluxoModelo);
        fluxoModelo.setCidade(null);
        return this;
    }

    public Set<EnderecoPessoa> getEnderecoPessoas() {
        return this.enderecoPessoas;
    }

    public void setEnderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        if (this.enderecoPessoas != null) {
            this.enderecoPessoas.forEach(i -> i.setCidade(null));
        }
        if (enderecoPessoas != null) {
            enderecoPessoas.forEach(i -> i.setCidade(this));
        }
        this.enderecoPessoas = enderecoPessoas;
    }

    public Cidade enderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        this.setEnderecoPessoas(enderecoPessoas);
        return this;
    }

    public Cidade addEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.add(enderecoPessoa);
        enderecoPessoa.setCidade(this);
        return this;
    }

    public Cidade removeEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.remove(enderecoPessoa);
        enderecoPessoa.setCidade(null);
        return this;
    }

    public Set<EnderecoEmpresa> getEnderecoEmpresas() {
        return this.enderecoEmpresas;
    }

    public void setEnderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        if (this.enderecoEmpresas != null) {
            this.enderecoEmpresas.forEach(i -> i.setCidade(null));
        }
        if (enderecoEmpresas != null) {
            enderecoEmpresas.forEach(i -> i.setCidade(this));
        }
        this.enderecoEmpresas = enderecoEmpresas;
    }

    public Cidade enderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        this.setEnderecoEmpresas(enderecoEmpresas);
        return this;
    }

    public Cidade addEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.add(enderecoEmpresa);
        enderecoEmpresa.setCidade(this);
        return this;
    }

    public Cidade removeEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.remove(enderecoEmpresa);
        enderecoEmpresa.setCidade(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Cidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", contratacao='" + getContratacao() + "'" +
            ", abertura='" + getAbertura() + "'" +
            "}";
    }
}
