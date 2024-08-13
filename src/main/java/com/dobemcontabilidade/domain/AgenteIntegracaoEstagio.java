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
 * A AgenteIntegracaoEstagio.
 */
@Entity
@Table(name = "agente_integracao_estagio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenteIntegracaoEstagio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cnpj")
    private String cnpj;

    @Size(max = 200)
    @Column(name = "razao_social", length = 200)
    private String razaoSocial;

    @Column(name = "coordenador")
    private String coordenador;

    @Column(name = "cpf_coordenador_estagio")
    private String cpfCoordenadorEstagio;

    @Column(name = "logradouro")
    private String logradouro;

    @Size(max = 5)
    @Column(name = "numero", length = 5)
    private String numero;

    @Size(max = 100)
    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Size(max = 9)
    @Column(name = "cep", length = 9)
    private String cep;

    @Column(name = "principal")
    private Boolean principal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agenteIntegracaoEstagio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "agenteIntegracaoEstagio", "instituicaoEnsino" }, allowSetters = true)
    private Set<ContratoFuncionario> contratoFuncionarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "instituicaoEnsinos",
            "agenteIntegracaoEstagios",
            "empresaModelos",
            "fluxoModelos",
            "enderecoPessoas",
            "enderecoEmpresas",
            "estado",
        },
        allowSetters = true
    )
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgenteIntegracaoEstagio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public AgenteIntegracaoEstagio cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public AgenteIntegracaoEstagio razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCoordenador() {
        return this.coordenador;
    }

    public AgenteIntegracaoEstagio coordenador(String coordenador) {
        this.setCoordenador(coordenador);
        return this;
    }

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }

    public String getCpfCoordenadorEstagio() {
        return this.cpfCoordenadorEstagio;
    }

    public AgenteIntegracaoEstagio cpfCoordenadorEstagio(String cpfCoordenadorEstagio) {
        this.setCpfCoordenadorEstagio(cpfCoordenadorEstagio);
        return this;
    }

    public void setCpfCoordenadorEstagio(String cpfCoordenadorEstagio) {
        this.cpfCoordenadorEstagio = cpfCoordenadorEstagio;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public AgenteIntegracaoEstagio logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public AgenteIntegracaoEstagio numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public AgenteIntegracaoEstagio complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return this.bairro;
    }

    public AgenteIntegracaoEstagio bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public AgenteIntegracaoEstagio cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public AgenteIntegracaoEstagio principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Set<ContratoFuncionario> getContratoFuncionarios() {
        return this.contratoFuncionarios;
    }

    public void setContratoFuncionarios(Set<ContratoFuncionario> contratoFuncionarios) {
        if (this.contratoFuncionarios != null) {
            this.contratoFuncionarios.forEach(i -> i.setAgenteIntegracaoEstagio(null));
        }
        if (contratoFuncionarios != null) {
            contratoFuncionarios.forEach(i -> i.setAgenteIntegracaoEstagio(this));
        }
        this.contratoFuncionarios = contratoFuncionarios;
    }

    public AgenteIntegracaoEstagio contratoFuncionarios(Set<ContratoFuncionario> contratoFuncionarios) {
        this.setContratoFuncionarios(contratoFuncionarios);
        return this;
    }

    public AgenteIntegracaoEstagio addContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        this.contratoFuncionarios.add(contratoFuncionario);
        contratoFuncionario.setAgenteIntegracaoEstagio(this);
        return this;
    }

    public AgenteIntegracaoEstagio removeContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        this.contratoFuncionarios.remove(contratoFuncionario);
        contratoFuncionario.setAgenteIntegracaoEstagio(null);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public AgenteIntegracaoEstagio cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgenteIntegracaoEstagio)) {
            return false;
        }
        return getId() != null && getId().equals(((AgenteIntegracaoEstagio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenteIntegracaoEstagio{" +
            "id=" + getId() +
            ", cnpj='" + getCnpj() + "'" +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", coordenador='" + getCoordenador() + "'" +
            ", cpfCoordenadorEstagio='" + getCpfCoordenadorEstagio() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cep='" + getCep() + "'" +
            ", principal='" + getPrincipal() + "'" +
            "}";
    }
}
