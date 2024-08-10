package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.DocumentoTarefa;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.service.dto.DocumentoTarefaDTO;
import com.dobemcontabilidade.service.dto.TarefaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentoTarefa} and its DTO {@link DocumentoTarefaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentoTarefaMapper extends EntityMapper<DocumentoTarefaDTO, DocumentoTarefa> {
    @Mapping(target = "tarefa", source = "tarefa", qualifiedByName = "tarefaTitulo")
    DocumentoTarefaDTO toDto(DocumentoTarefa s);

    @Named("tarefaTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    TarefaDTO toDtoTarefaTitulo(Tarefa tarefa);
}
