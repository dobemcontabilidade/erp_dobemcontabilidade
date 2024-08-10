package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AreaContabilEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabilEmpresaDTO implements Serializable {

    private Long id;

    private Double pontuacao;

    private String depoimento;

    private String reclamacao;

    @NotNull
    private ContadorDTO contador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getDepoimento() {
        return depoimento;
    }

    public void setDepoimento(String depoimento) {
        this.depoimento = depoimento;
    }

    public String getReclamacao() {
        return reclamacao;
    }

    public void setReclamacao(String reclamacao) {
        this.reclamacao = reclamacao;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaContabilEmpresaDTO)) {
            return false;
        }

        AreaContabilEmpresaDTO areaContabilEmpresaDTO = (AreaContabilEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, areaContabilEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabilEmpresaDTO{" +
            "id=" + getId() +
            ", pontuacao=" + getPontuacao() +
            ", depoimento='" + getDepoimento() + "'" +
            ", reclamacao='" + getReclamacao() + "'" +
            ", contador=" + getContador() +
            "}";
    }
}
