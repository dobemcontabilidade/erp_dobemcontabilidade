package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.PerfilContadorAreaContabil;
import com.dobemcontabilidade.service.dto.AreaContabilDTO;
import com.dobemcontabilidade.service.dto.PerfilContadorAreaContabilDTO;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerfilContadorAreaContabil} and its DTO {@link PerfilContadorAreaContabilDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerfilContadorAreaContabilMapper extends EntityMapper<PerfilContadorAreaContabilDTO, PerfilContadorAreaContabil> {
    @Mapping(target = "areaContabil", source = "areaContabil", qualifiedByName = "areaContabilNome")
    @Mapping(target = "perfilContador", source = "perfilContador", qualifiedByName = "perfilContadorPerfil")
    PerfilContadorAreaContabilDTO toDto(PerfilContadorAreaContabil s);

    @Named("areaContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AreaContabilDTO toDtoAreaContabilNome(AreaContabil areaContabil);

    @Named("perfilContadorPerfil")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "perfil", source = "perfil")
    PerfilContadorDTO toDtoPerfilContadorPerfil(PerfilContador perfilContador);
}
