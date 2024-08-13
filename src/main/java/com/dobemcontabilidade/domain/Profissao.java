package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profissao.
 */
@Entity
@Table(name = "profissao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profissao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cbo")
    private Integer cbo;

    @Column(name = "categoria")
    private String categoria;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "usuarioEmpresa",
            "estrangeiros",
            "contratoFuncionarios",
            "demissaoFuncionarios",
            "dependentesFuncionarios",
            "empresaVinculadas",
            "departamentoFuncionarios",
            "pessoa",
            "empresa",
            "profissao",
        },
        allowSetters = true
    )
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "usuarioEmpresa", "empresa", "profissao" }, allowSetters = true)
    private Set<Socio> socios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profissao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Profissao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCbo() {
        return this.cbo;
    }

    public Profissao cbo(Integer cbo) {
        this.setCbo(cbo);
        return this;
    }

    public void setCbo(Integer cbo) {
        this.cbo = cbo;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Profissao categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Profissao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        if (this.funcionarios != null) {
            this.funcionarios.forEach(i -> i.setProfissao(null));
        }
        if (funcionarios != null) {
            funcionarios.forEach(i -> i.setProfissao(this));
        }
        this.funcionarios = funcionarios;
    }

    public Profissao funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public Profissao addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setProfissao(this);
        return this;
    }

    public Profissao removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setProfissao(null);
        return this;
    }

    public Set<Socio> getSocios() {
        return this.socios;
    }

    public void setSocios(Set<Socio> socios) {
        if (this.socios != null) {
            this.socios.forEach(i -> i.setProfissao(null));
        }
        if (socios != null) {
            socios.forEach(i -> i.setProfissao(this));
        }
        this.socios = socios;
    }

    public Profissao socios(Set<Socio> socios) {
        this.setSocios(socios);
        return this;
    }

    public Profissao addSocio(Socio socio) {
        this.socios.add(socio);
        socio.setProfissao(this);
        return this;
    }

    public Profissao removeSocio(Socio socio) {
        this.socios.remove(socio);
        socio.setProfissao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profissao)) {
            return false;
        }
        return getId() != null && getId().equals(((Profissao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profissao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cbo=" + getCbo() +
            ", categoria='" + getCategoria() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
