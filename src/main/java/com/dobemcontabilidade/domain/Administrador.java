package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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
    @Column(name = "sobrenome", length = 200, nullable = false)
    private String sobrenome;

    @Column(name = "funcao")
    private String funcao;

    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoPessoas",
            "escolaridadePessoas",
            "bancoPessoas",
            "dependentesFuncionarios",
            "enderecoPessoas",
            "emails",
            "telefones",
            "administrador",
            "contador",
            "socio",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

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

    public String getSobrenome() {
        return this.sobrenome;
    }

    public Administrador sobrenome(String sobrenome) {
        this.setSobrenome(sobrenome);
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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
            ", sobrenome='" + getSobrenome() + "'" +
            ", funcao='" + getFuncao() + "'" +
            "}";
    }
}
