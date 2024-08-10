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
 * A Administrador.
 */
@Entity
@Table(name = "administrador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;

    @NotNull
    @Size(max = 200)
    @Column(name = "sobre_nome", length = 200, nullable = false)
    private String sobreNome;

    @Column(name = "funcao")
    private String funcao;

    @JsonIgnoreProperties(
        value = {
            "enderecoPessoas", "anexoPessoas", "emails", "telefones", "usuarioEmpresa", "administrador", "contador", "funcionario", "socio",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "administrador" }, allowSetters = true)
    private Set<UsuarioContador> usuarioContadors = new HashSet<>();

    @JsonIgnoreProperties(value = { "administrador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "administrador")
    private UsuarioErp usuarioErp;

    @JsonIgnoreProperties(value = { "administrador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "administrador")
    private UsuarioGestao usuarioGestao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Administrador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Administrador nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return this.sobreNome;
    }

    public Administrador sobreNome(String sobreNome) {
        this.setSobreNome(sobreNome);
        return this;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public Administrador funcao(String funcao) {
        this.setFuncao(funcao);
        return this;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Administrador pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public Set<UsuarioContador> getUsuarioContadors() {
        return this.usuarioContadors;
    }

    public void setUsuarioContadors(Set<UsuarioContador> usuarioContadors) {
        if (this.usuarioContadors != null) {
            this.usuarioContadors.forEach(i -> i.setAdministrador(null));
        }
        if (usuarioContadors != null) {
            usuarioContadors.forEach(i -> i.setAdministrador(this));
        }
        this.usuarioContadors = usuarioContadors;
    }

    public Administrador usuarioContadors(Set<UsuarioContador> usuarioContadors) {
        this.setUsuarioContadors(usuarioContadors);
        return this;
    }

    public Administrador addUsuarioContador(UsuarioContador usuarioContador) {
        this.usuarioContadors.add(usuarioContador);
        usuarioContador.setAdministrador(this);
        return this;
    }

    public Administrador removeUsuarioContador(UsuarioContador usuarioContador) {
        this.usuarioContadors.remove(usuarioContador);
        usuarioContador.setAdministrador(null);
        return this;
    }

    public UsuarioErp getUsuarioErp() {
        return this.usuarioErp;
    }

    public void setUsuarioErp(UsuarioErp usuarioErp) {
        if (this.usuarioErp != null) {
            this.usuarioErp.setAdministrador(null);
        }
        if (usuarioErp != null) {
            usuarioErp.setAdministrador(this);
        }
        this.usuarioErp = usuarioErp;
    }

    public Administrador usuarioErp(UsuarioErp usuarioErp) {
        this.setUsuarioErp(usuarioErp);
        return this;
    }

    public UsuarioGestao getUsuarioGestao() {
        return this.usuarioGestao;
    }

    public void setUsuarioGestao(UsuarioGestao usuarioGestao) {
        if (this.usuarioGestao != null) {
            this.usuarioGestao.setAdministrador(null);
        }
        if (usuarioGestao != null) {
            usuarioGestao.setAdministrador(this);
        }
        this.usuarioGestao = usuarioGestao;
    }

    public Administrador usuarioGestao(UsuarioGestao usuarioGestao) {
        this.setUsuarioGestao(usuarioGestao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Administrador)) {
            return false;
        }
        return getId() != null && getId().equals(((Administrador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Administrador{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobreNome='" + getSobreNome() + "'" +
            ", funcao='" + getFuncao() + "'" +
            "}";
    }
}
