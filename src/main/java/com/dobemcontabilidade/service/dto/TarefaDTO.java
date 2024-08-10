package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.TipoTarefaEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Tarefa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaDTO implements Serializable {

    private Long id;

    private String titulo;

    private Integer numeroDias;

    private Boolean diaUtil;

    private Double valor;

    private Boolean notificarCliente;

    private Boolean geraMulta;

    private Boolean exibirEmpresa;

    private Instant dataLegal;

    private Integer pontos;

    private TipoTarefaEnum tipoTarefa;

    @NotNull
    private EsferaDTO esfera;

    @NotNull
    private FrequenciaDTO frequencia;

    @NotNull
    private CompetenciaDTO competencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }

    public Boolean getDiaUtil() {
        return diaUtil;
    }

    public void setDiaUtil(Boolean diaUtil) {
        this.diaUtil = diaUtil;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getNotificarCliente() {
        return notificarCliente;
    }

    public void setNotificarCliente(Boolean notificarCliente) {
        this.notificarCliente = notificarCliente;
    }

    public Boolean getGeraMulta() {
        return geraMulta;
    }

    public void setGeraMulta(Boolean geraMulta) {
        this.geraMulta = geraMulta;
    }

    public Boolean getExibirEmpresa() {
        return exibirEmpresa;
    }

    public void setExibirEmpresa(Boolean exibirEmpresa) {
        this.exibirEmpresa = exibirEmpresa;
    }

    public Instant getDataLegal() {
        return dataLegal;
    }

    public void setDataLegal(Instant dataLegal) {
        this.dataLegal = dataLegal;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public TipoTarefaEnum getTipoTarefa() {
        return tipoTarefa;
    }

    public void setTipoTarefa(TipoTarefaEnum tipoTarefa) {
        this.tipoTarefa = tipoTarefa;
    }

    public EsferaDTO getEsfera() {
        return esfera;
    }

    public void setEsfera(EsferaDTO esfera) {
        this.esfera = esfera;
    }

    public FrequenciaDTO getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(FrequenciaDTO frequencia) {
        this.frequencia = frequencia;
    }

    public CompetenciaDTO getCompetencia() {
        return competencia;
    }

    public void setCompetencia(CompetenciaDTO competencia) {
        this.competencia = competencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaDTO)) {
            return false;
        }

        TarefaDTO tarefaDTO = (TarefaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tarefaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", numeroDias=" + getNumeroDias() +
            ", diaUtil='" + getDiaUtil() + "'" +
            ", valor=" + getValor() +
            ", notificarCliente='" + getNotificarCliente() + "'" +
            ", geraMulta='" + getGeraMulta() + "'" +
            ", exibirEmpresa='" + getExibirEmpresa() + "'" +
            ", dataLegal='" + getDataLegal() + "'" +
            ", pontos=" + getPontos() +
            ", tipoTarefa='" + getTipoTarefa() + "'" +
            ", esfera=" + getEsfera() +
            ", frequencia=" + getFrequencia() +
            ", competencia=" + getCompetencia() +
            "}";
    }
}
