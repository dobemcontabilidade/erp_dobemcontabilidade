package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoRecorrenciaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TarefaRecorrenteEmpresaModelo.
 */
@Entity
@Table(name = "taf_reco_empresa_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaRecorrenteEmpresaModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dia_admin")
    private Integer diaAdmin;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes_legal")
    private MesCompetenciaEnum mesLegal;

    @Enumerated(EnumType.STRING)
    @Column(name = "recorrencia")
    private TipoRecorrenciaEnum recorrencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tarefaRecorrenteEmpresaModelos", "empresaModelo", "servicoContabil" }, allowSetters = true)
    private ServicoContabilEmpresaModelo servicoContabilEmpresaModelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TarefaRecorrenteEmpresaModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiaAdmin() {
        return this.diaAdmin;
    }

    public TarefaRecorrenteEmpresaModelo diaAdmin(Integer diaAdmin) {
        this.setDiaAdmin(diaAdmin);
        return this;
    }

    public void setDiaAdmin(Integer diaAdmin) {
        this.diaAdmin = diaAdmin;
    }

    public MesCompetenciaEnum getMesLegal() {
        return this.mesLegal;
    }

    public TarefaRecorrenteEmpresaModelo mesLegal(MesCompetenciaEnum mesLegal) {
        this.setMesLegal(mesLegal);
        return this;
    }

    public void setMesLegal(MesCompetenciaEnum mesLegal) {
        this.mesLegal = mesLegal;
    }

    public TipoRecorrenciaEnum getRecorrencia() {
        return this.recorrencia;
    }

    public TarefaRecorrenteEmpresaModelo recorrencia(TipoRecorrenciaEnum recorrencia) {
        this.setRecorrencia(recorrencia);
        return this;
    }

    public void setRecorrencia(TipoRecorrenciaEnum recorrencia) {
        this.recorrencia = recorrencia;
    }

    public ServicoContabilEmpresaModelo getServicoContabilEmpresaModelo() {
        return this.servicoContabilEmpresaModelo;
    }

    public void setServicoContabilEmpresaModelo(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        this.servicoContabilEmpresaModelo = servicoContabilEmpresaModelo;
    }

    public TarefaRecorrenteEmpresaModelo servicoContabilEmpresaModelo(ServicoContabilEmpresaModelo servicoContabilEmpresaModelo) {
        this.setServicoContabilEmpresaModelo(servicoContabilEmpresaModelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaRecorrenteEmpresaModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaRecorrenteEmpresaModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaRecorrenteEmpresaModelo{" +
            "id=" + getId() +
            ", diaAdmin=" + getDiaAdmin() +
            ", mesLegal='" + getMesLegal() + "'" +
            ", recorrencia='" + getRecorrencia() + "'" +
            "}";
    }
}
