package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.Estado;
import com.dobemcontabilidade.service.dto.CidadeDTO;
import com.dobemcontabilidade.service.dto.EstadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidade} and its DTO {@link CidadeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CidadeMapper extends EntityMapper<CidadeDTO, Cidade> {
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoNome")
    CidadeDTO toDto(Cidade s);

    @Named("estadoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EstadoDTO toDtoEstadoNome(Estado estado);
}
