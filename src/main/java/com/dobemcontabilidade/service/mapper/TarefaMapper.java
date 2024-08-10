package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.service.dto.CompetenciaDTO;
import com.dobemcontabilidade.service.dto.EsferaDTO;
import com.dobemcontabilidade.service.dto.FrequenciaDTO;
import com.dobemcontabilidade.service.dto.TarefaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarefa} and its DTO {@link TarefaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TarefaMapper extends EntityMapper<TarefaDTO, Tarefa> {
    @Mapping(target = "esfera", source = "esfera", qualifiedByName = "esferaNome")
    @Mapping(target = "frequencia", source = "frequencia", qualifiedByName = "frequenciaNome")
    @Mapping(target = "competencia", source = "competencia", qualifiedByName = "competenciaNome")
    TarefaDTO toDto(Tarefa s);

    @Named("esferaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EsferaDTO toDtoEsferaNome(Esfera esfera);

    @Named("frequenciaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    FrequenciaDTO toDtoFrequenciaNome(Frequencia frequencia);

    @Named("competenciaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CompetenciaDTO toDtoCompetenciaNome(Competencia competencia);
}
