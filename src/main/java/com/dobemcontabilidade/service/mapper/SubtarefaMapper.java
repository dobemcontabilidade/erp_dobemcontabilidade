package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Subtarefa;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.service.dto.SubtarefaDTO;
import com.dobemcontabilidade.service.dto.TarefaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subtarefa} and its DTO {@link SubtarefaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubtarefaMapper extends EntityMapper<SubtarefaDTO, Subtarefa> {
    @Mapping(target = "tarefa", source = "tarefa", qualifiedByName = "tarefaTitulo")
    SubtarefaDTO toDto(Subtarefa s);

    @Named("tarefaTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    TarefaDTO toDtoTarefaTitulo(Tarefa tarefa);
}
