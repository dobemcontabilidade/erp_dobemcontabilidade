package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EscolaridadePessoa.
 */
@Entity
@Table(name = "escolaridade_pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EscolaridadePessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_instituicao", nullable = false)
    private String nomeInstituicao;

    @Column(name = "ano_conclusao")
    private Integer anoConclusao;

    @Column(name = "url_comprovante_escolaridade")
    private String urlComprovanteEscolaridade;

    @ManyToOne(optional = false)
    @NotNull
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
    private Pessoa pessoa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "escolaridadePessoas" }, allowSetters = true)
    private Escolaridade escolaridade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EscolaridadePessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeInstituicao() {
        return this.nomeInstituicao;
    }

    public EscolaridadePessoa nomeInstituicao(String nomeInstituicao) {
        this.setNomeInstituicao(nomeInstituicao);
        return this;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public Integer getAnoConclusao() {
        return this.anoConclusao;
    }

    public EscolaridadePessoa anoConclusao(Integer anoConclusao) {
        this.setAnoConclusao(anoConclusao);
        return this;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public String getUrlComprovanteEscolaridade() {
        return this.urlComprovanteEscolaridade;
    }

    public EscolaridadePessoa urlComprovanteEscolaridade(String urlComprovanteEscolaridade) {
        this.setUrlComprovanteEscolaridade(urlComprovanteEscolaridade);
        return this;
    }

    public void setUrlComprovanteEscolaridade(String urlComprovanteEscolaridade) {
        this.urlComprovanteEscolaridade = urlComprovanteEscolaridade;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public EscolaridadePessoa pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public Escolaridade getEscolaridade() {
        return this.escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public EscolaridadePessoa escolaridade(Escolaridade escolaridade) {
        this.setEscolaridade(escolaridade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EscolaridadePessoa)) {
            return false;
        }
        return getId() != null && getId().equals(((EscolaridadePessoa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EscolaridadePessoa{" +
            "id=" + getId() +
            ", nomeInstituicao='" + getNomeInstituicao() + "'" +
            ", anoConclusao=" + getAnoConclusao() +
            ", urlComprovanteEscolaridade='" + getUrlComprovanteEscolaridade() + "'" +
            "}";
    }
}
