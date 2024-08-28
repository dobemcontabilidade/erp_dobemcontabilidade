package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocsPessoa.
 */
@Entity
@Table(name = "docs_pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocsPessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "url_arquivo", nullable = false)
    private String urlArquivo;

    @Column(name = "tipo")
    private String tipo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "enderecoPessoas", "docsPessoas", "emails", "telefones", "socio" }, allowSetters = true)
    private PessoaFisica pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocsPessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlArquivo() {
        return this.urlArquivo;
    }

    public DocsPessoa urlArquivo(String urlArquivo) {
        this.setUrlArquivo(urlArquivo);
        return this;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public DocsPessoa tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DocsPessoa descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PessoaFisica getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(PessoaFisica pessoaFisica) {
        this.pessoa = pessoaFisica;
    }

    public DocsPessoa pessoa(PessoaFisica pessoaFisica) {
        this.setPessoa(pessoaFisica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocsPessoa)) {
            return false;
        }
        return getId() != null && getId().equals(((DocsPessoa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocsPessoa{" +
            "id=" + getId() +
            ", urlArquivo='" + getUrlArquivo() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
