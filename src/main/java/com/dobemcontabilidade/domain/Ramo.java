package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ramo.
 */
@Entity
@Table(name = "ramo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ramo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "periodoPagamento",
            "planoContaAzul",
            "planoContabil",
            "ramo",
            "tributacao",
            "descontoPlanoContabil",
            "descontoPlanoContaAzul",
            "assinaturaEmpresa",
        },
        allowSetters = true
    )
    private Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ramo", "planoContabil" }, allowSetters = true)
    private Set<AdicionalRamo> adicionalRamos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ramo", "planoContabil" }, allowSetters = true)
    private Set<ValorBaseRamo> valorBaseRamos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subclasseCnaes", "ramo", "empresas" }, allowSetters = true)
    private Set<SegmentoCnae> segmentoCnaes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ramo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Ramo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Ramo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<CalculoPlanoAssinatura> getCalculoPlanoAssinaturas() {
        return this.calculoPlanoAssinaturas;
    }

    public void setCalculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        if (this.calculoPlanoAssinaturas != null) {
            this.calculoPlanoAssinaturas.forEach(i -> i.setRamo(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setRamo(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public Ramo calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public Ramo addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setRamo(this);
        return this;
    }

    public Ramo removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setRamo(null);
        return this;
    }

    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        if (this.empresas != null) {
            this.empresas.forEach(i -> i.setRamo(null));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.setRamo(this));
        }
        this.empresas = empresas;
    }

    public Ramo empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public Ramo addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setRamo(this);
        return this;
    }

    public Ramo removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setRamo(null);
        return this;
    }

    public Set<AdicionalRamo> getAdicionalRamos() {
        return this.adicionalRamos;
    }

    public void setAdicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        if (this.adicionalRamos != null) {
            this.adicionalRamos.forEach(i -> i.setRamo(null));
        }
        if (adicionalRamos != null) {
            adicionalRamos.forEach(i -> i.setRamo(this));
        }
        this.adicionalRamos = adicionalRamos;
    }

    public Ramo adicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        this.setAdicionalRamos(adicionalRamos);
        return this;
    }

    public Ramo addAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.add(adicionalRamo);
        adicionalRamo.setRamo(this);
        return this;
    }

    public Ramo removeAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.remove(adicionalRamo);
        adicionalRamo.setRamo(null);
        return this;
    }

    public Set<ValorBaseRamo> getValorBaseRamos() {
        return this.valorBaseRamos;
    }

    public void setValorBaseRamos(Set<ValorBaseRamo> valorBaseRamos) {
        if (this.valorBaseRamos != null) {
            this.valorBaseRamos.forEach(i -> i.setRamo(null));
        }
        if (valorBaseRamos != null) {
            valorBaseRamos.forEach(i -> i.setRamo(this));
        }
        this.valorBaseRamos = valorBaseRamos;
    }

    public Ramo valorBaseRamos(Set<ValorBaseRamo> valorBaseRamos) {
        this.setValorBaseRamos(valorBaseRamos);
        return this;
    }

    public Ramo addValorBaseRamo(ValorBaseRamo valorBaseRamo) {
        this.valorBaseRamos.add(valorBaseRamo);
        valorBaseRamo.setRamo(this);
        return this;
    }

    public Ramo removeValorBaseRamo(ValorBaseRamo valorBaseRamo) {
        this.valorBaseRamos.remove(valorBaseRamo);
        valorBaseRamo.setRamo(null);
        return this;
    }

    public Set<SegmentoCnae> getSegmentoCnaes() {
        return this.segmentoCnaes;
    }

    public void setSegmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        if (this.segmentoCnaes != null) {
            this.segmentoCnaes.forEach(i -> i.setRamo(null));
        }
        if (segmentoCnaes != null) {
            segmentoCnaes.forEach(i -> i.setRamo(this));
        }
        this.segmentoCnaes = segmentoCnaes;
    }

    public Ramo segmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        this.setSegmentoCnaes(segmentoCnaes);
        return this;
    }

    public Ramo addSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.add(segmentoCnae);
        segmentoCnae.setRamo(this);
        return this;
    }

    public Ramo removeSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.remove(segmentoCnae);
        segmentoCnae.setRamo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ramo)) {
            return false;
        }
        return getId() != null && getId().equals(((Ramo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ramo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
