package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Subtarefa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubtarefaDTO implements Serializable {

    private Long id;

    private Integer ordem;

    private String item;

    @Lob
    private String descricao;

    @NotNull
    private TarefaDTO tarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(o instanceof SubtarefaDTO)) {
            return false;
        }

        SubtarefaDTO subtarefaDTO = (SubtarefaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subtarefaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubtarefaDTO{" +
            "id=" + getId() +
            ", ordem=" + getOrdem() +
            ", item='" + getItem() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tarefa=" + getTarefa() +
            "}";
    }
}
