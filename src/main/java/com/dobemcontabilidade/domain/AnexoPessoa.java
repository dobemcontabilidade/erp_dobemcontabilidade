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
 * A AnexoPessoa.
 */
@Entity
@Table(name = "anexo_pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoPessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "url_arquivo")
    private String urlArquivo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "anexoPessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoPessoa", "anexoRequerido" }, allowSetters = true)
    private Set<AnexoRequeridoPessoa> anexoRequeridoPessoas = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoPessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlArquivo() {
        return this.urlArquivo;
    }

    public AnexoPessoa urlArquivo(String urlArquivo) {
        this.setUrlArquivo(urlArquivo);
        return this;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AnexoPessoa descricao(String descricao) {
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
            this.anexoRequeridoPessoas.forEach(i -> i.setAnexoPessoa(null));
        }
        if (anexoRequeridoPessoas != null) {
            anexoRequeridoPessoas.forEach(i -> i.setAnexoPessoa(this));
        }
        this.anexoRequeridoPessoas = anexoRequeridoPessoas;
    }

    public AnexoPessoa anexoRequeridoPessoas(Set<AnexoRequeridoPessoa> anexoRequeridoPessoas) {
        this.setAnexoRequeridoPessoas(anexoRequeridoPessoas);
        return this;
    }

    public AnexoPessoa addAnexoRequeridoPessoa(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        this.anexoRequeridoPessoas.add(anexoRequeridoPessoa);
        anexoRequeridoPessoa.setAnexoPessoa(this);
        return this;
    }

    public AnexoPessoa removeAnexoRequeridoPessoa(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        this.anexoRequeridoPessoas.remove(anexoRequeridoPessoa);
        anexoRequeridoPessoa.setAnexoPessoa(null);
        return this;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public AnexoPessoa pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoPessoa)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoPessoa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoPessoa{" +
            "id=" + getId() +
            ", urlArquivo='" + getUrlArquivo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
