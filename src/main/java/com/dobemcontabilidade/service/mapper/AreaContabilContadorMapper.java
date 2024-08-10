package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.domain.AreaContabilContador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.service.dto.AreaContabilContadorDTO;
import com.dobemcontabilidade.service.dto.AreaContabilDTO;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaContabilContador} and its DTO {@link AreaContabilContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaContabilContadorMapper extends EntityMapper<AreaContabilContadorDTO, AreaContabilContador> {
    @Mapping(target = "areaContabil", source = "areaContabil", qualifiedByName = "areaContabilNome")
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    AreaContabilContadorDTO toDto(AreaContabilContador s);

    @Named("areaContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AreaContabilDTO toDtoAreaContabilNome(AreaContabil areaContabil);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);
}
