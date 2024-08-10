package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoFuncionarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "ctps")
    private String ctps;

    @Column(name = "cargo")
    private String cargo;

    @Lob
    @Column(name = "descricao_atividades")
    private String descricaoAtividades;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoFuncionarioEnum situacao;

    @JsonIgnoreProperties(
        value = {
            "enderecoPessoas", "anexoPessoas", "emails", "telefones", "administrador", "contador", "funcionario", "socio", "usuarioEmpresa",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "departamento" }, allowSetters = true)
    private Set<DepartamentoFuncionario> departamentoFuncionarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "assinaturaEmpresas",
            "funcionarios",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "anexoEmpresas",
            "certificadoDigitals",
            "usuarioEmpresas",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "segmentoCnaes",
            "ramo",
            "tributacao",
            "enquadramento",
        },
        allowSetters = true
    )
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Funcionario nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return this.salario;
    }

    public Funcionario salario(Double salario) {
        this.setSalario(salario);
        return this;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCtps() {
        return this.ctps;
    }

    public Funcionario ctps(String ctps) {
        this.setCtps(ctps);
        return this;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getCargo() {
        return this.cargo;
    }

    public Funcionario cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescricaoAtividades() {
        return this.descricaoAtividades;
    }

    public Funcionario descricaoAtividades(String descricaoAtividades) {
        this.setDescricaoAtividades(descricaoAtividades);
        return this;
    }

    public void setDescricaoAtividades(String descricaoAtividades) {
        this.descricaoAtividades = descricaoAtividades;
    }

    public SituacaoFuncionarioEnum getSituacao() {
        return this.situacao;
    }

    public Funcionario situacao(SituacaoFuncionarioEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoFuncionarioEnum situacao) {
        this.situacao = situacao;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Funcionario pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public Set<DepartamentoFuncionario> getDepartamentoFuncionarios() {
        return this.departamentoFuncionarios;
    }

    public void setDepartamentoFuncionarios(Set<DepartamentoFuncionario> departamentoFuncionarios) {
        if (this.departamentoFuncionarios != null) {
            this.departamentoFuncionarios.forEach(i -> i.setFuncionario(null));
        }
        if (departamentoFuncionarios != null) {
            departamentoFuncionarios.forEach(i -> i.setFuncionario(this));
        }
        this.departamentoFuncionarios = departamentoFuncionarios;
    }

    public Funcionario departamentoFuncionarios(Set<DepartamentoFuncionario> departamentoFuncionarios) {
        this.setDepartamentoFuncionarios(departamentoFuncionarios);
        return this;
    }

    public Funcionario addDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        this.departamentoFuncionarios.add(departamentoFuncionario);
        departamentoFuncionario.setFuncionario(this);
        return this;
    }

    public Funcionario removeDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        this.departamentoFuncionarios.remove(departamentoFuncionario);
        departamentoFuncionario.setFuncionario(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Funcionario empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return getId() != null && getId().equals(((Funcionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", salario=" + getSalario() +
            ", ctps='" + getCtps() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", descricaoAtividades='" + getDescricaoAtividades() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
