package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.TarefaEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaEmpresaDTO implements Serializable {

    private Long id;

    private Instant dataHora;

    @NotNull
    private EmpresaDTO empresa;

    @NotNull
    private ContadorDTO contador;

    @NotNull
    private TarefaDTO tarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    public TarefaDTO getTarefa() {
        return tarefa;
    }

    public void setTarefa(TarefaDTO tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaEmpresaDTO)) {
            return false;
        }

        TarefaEmpresaDTO tarefaEmpresaDTO = (TarefaEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tarefaEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaEmpresaDTO{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", empresa=" + getEmpresa() +
            ", contador=" + getContador() +
            ", tarefa=" + getTarefa() +
            "}";
    }
}
