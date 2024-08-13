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
 * A PerfilContador.
 */
@Entity
@Table(name = "perfil_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "perfil", nullable = false)
    private String perfil;

    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "limite_empresas")
    private Integer limiteEmpresas;

    @Column(name = "limite_departamentos")
    private Integer limiteDepartamentos;

    @Column(name = "limite_faturamento")
    private Double limiteFaturamento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perfilContador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "usuarioContador",
            "areaContabilAssinaturaEmpresas",
            "feedBackContadorParaUsuarios",
            "ordemServicos",
            "areaContabilContadors",
            "contadorResponsavelOrdemServicos",
            "contadorResponsavelTarefaRecorrentes",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
        },
        allowSetters = true
    )
    private Set<Contador> contadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perfilContador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "perfilContador" }, allowSetters = true)
    private Set<PerfilContadorDepartamento> perfilContadorDepartamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerfilContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public PerfilContador perfil(String perfil) {
        this.setPerfil(perfil);
        return this;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public PerfilContador descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLimiteEmpresas() {
        return this.limiteEmpresas;
    }

    public PerfilContador limiteEmpresas(Integer limiteEmpresas) {
        this.setLimiteEmpresas(limiteEmpresas);
        return this;
    }

    public void setLimiteEmpresas(Integer limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public Integer getLimiteDepartamentos() {
        return this.limiteDepartamentos;
    }

    public PerfilContador limiteDepartamentos(Integer limiteDepartamentos) {
        this.setLimiteDepartamentos(limiteDepartamentos);
        return this;
    }

    public void setLimiteDepartamentos(Integer limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public Double getLimiteFaturamento() {
        return this.limiteFaturamento;
    }

    public PerfilContador limiteFaturamento(Double limiteFaturamento) {
        this.setLimiteFaturamento(limiteFaturamento);
        return this;
    }

    public void setLimiteFaturamento(Double limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    public Set<Contador> getContadors() {
        return this.contadors;
    }

    public void setContadors(Set<Contador> contadors) {
        if (this.contadors != null) {
            this.contadors.forEach(i -> i.setPerfilContador(null));
        }
        if (contadors != null) {
            contadors.forEach(i -> i.setPerfilContador(this));
        }
        this.contadors = contadors;
    }

    public PerfilContador contadors(Set<Contador> contadors) {
        this.setContadors(contadors);
        return this;
    }

    public PerfilContador addContador(Contador contador) {
        this.contadors.add(contador);
        contador.setPerfilContador(this);
        return this;
    }

    public PerfilContador removeContador(Contador contador) {
        this.contadors.remove(contador);
        contador.setPerfilContador(null);
        return this;
    }

    public Set<PerfilContadorDepartamento> getPerfilContadorDepartamentos() {
        return this.perfilContadorDepartamentos;
    }

    public void setPerfilContadorDepartamentos(Set<PerfilContadorDepartamento> perfilContadorDepartamentos) {
        if (this.perfilContadorDepartamentos != null) {
            this.perfilContadorDepartamentos.forEach(i -> i.setPerfilContador(null));
        }
        if (perfilContadorDepartamentos != null) {
            perfilContadorDepartamentos.forEach(i -> i.setPerfilContador(this));
        }
        this.perfilContadorDepartamentos = perfilContadorDepartamentos;
    }

    public PerfilContador perfilContadorDepartamentos(Set<PerfilContadorDepartamento> perfilContadorDepartamentos) {
        this.setPerfilContadorDepartamentos(perfilContadorDepartamentos);
        return this;
    }

    public PerfilContador addPerfilContadorDepartamento(PerfilContadorDepartamento perfilContadorDepartamento) {
        this.perfilContadorDepartamentos.add(perfilContadorDepartamento);
        perfilContadorDepartamento.setPerfilContador(this);
        return this;
    }

    public PerfilContador removePerfilContadorDepartamento(PerfilContadorDepartamento perfilContadorDepartamento) {
        this.perfilContadorDepartamentos.remove(perfilContadorDepartamento);
        perfilContadorDepartamento.setPerfilContador(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilContador)) {
            return false;
        }
        return getId() != null && getId().equals(((PerfilContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilContador{" +
            "id=" + getId() +
            ", perfil='" + getPerfil() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", limiteEmpresas=" + getLimiteEmpresas() +
            ", limiteDepartamentos=" + getLimiteDepartamentos() +
            ", limiteFaturamento=" + getLimiteFaturamento() +
            "}";
    }
}
