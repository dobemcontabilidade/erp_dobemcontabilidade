package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.DocumentoTarefa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentoTarefaDTO implements Serializable {

    private Long id;

    private String nome;

    @NotNull
    private TarefaDTO tarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(o instanceof DocumentoTarefaDTO)) {
            return false;
        }

        DocumentoTarefaDTO documentoTarefaDTO = (DocumentoTarefaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentoTarefaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentoTarefaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tarefa=" + getTarefa() +
            "}";
    }
}
