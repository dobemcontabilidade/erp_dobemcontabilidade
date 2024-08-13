package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estrangeiro.
 */
@Entity
@Table(name = "estrangeiro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estrangeiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_chegada")
    private String dataChegada;

    @Column(name = "data_naturalizacao")
    private String dataNaturalizacao;

    @Column(name = "casado_com_brasileiro")
    private Boolean casadoComBrasileiro;

    @Column(name = "filhos_com_brasileiro")
    private Boolean filhosComBrasileiro;

    @Column(name = "checked")
    private Boolean checked;

    @ManyToOne(optional = false)
    @NotNull
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
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estrangeiro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataChegada() {
        return this.dataChegada;
    }

    public Estrangeiro dataChegada(String dataChegada) {
        this.setDataChegada(dataChegada);
        return this;
    }

    public void setDataChegada(String dataChegada) {
        this.dataChegada = dataChegada;
    }

    public String getDataNaturalizacao() {
        return this.dataNaturalizacao;
    }

    public Estrangeiro dataNaturalizacao(String dataNaturalizacao) {
        this.setDataNaturalizacao(dataNaturalizacao);
        return this;
    }

    public void setDataNaturalizacao(String dataNaturalizacao) {
        this.dataNaturalizacao = dataNaturalizacao;
    }

    public Boolean getCasadoComBrasileiro() {
        return this.casadoComBrasileiro;
    }

    public Estrangeiro casadoComBrasileiro(Boolean casadoComBrasileiro) {
        this.setCasadoComBrasileiro(casadoComBrasileiro);
        return this;
    }

    public void setCasadoComBrasileiro(Boolean casadoComBrasileiro) {
        this.casadoComBrasileiro = casadoComBrasileiro;
    }

    public Boolean getFilhosComBrasileiro() {
        return this.filhosComBrasileiro;
    }

    public Estrangeiro filhosComBrasileiro(Boolean filhosComBrasileiro) {
        this.setFilhosComBrasileiro(filhosComBrasileiro);
        return this;
    }

    public void setFilhosComBrasileiro(Boolean filhosComBrasileiro) {
        this.filhosComBrasileiro = filhosComBrasileiro;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public Estrangeiro checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Estrangeiro funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estrangeiro)) {
            return false;
        }
        return getId() != null && getId().equals(((Estrangeiro) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estrangeiro{" +
            "id=" + getId() +
            ", dataChegada='" + getDataChegada() + "'" +
            ", dataNaturalizacao='" + getDataNaturalizacao() + "'" +
            ", casadoComBrasileiro='" + getCasadoComBrasileiro() + "'" +
            ", filhosComBrasileiro='" + getFilhosComBrasileiro() + "'" +
            ", checked='" + getChecked() + "'" +
            "}";
    }
}
