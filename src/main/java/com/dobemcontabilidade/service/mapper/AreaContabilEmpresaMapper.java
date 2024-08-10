package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AreaContabilEmpresa;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.service.dto.AreaContabilEmpresaDTO;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaContabilEmpresa} and its DTO {@link AreaContabilEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaContabilEmpresaMapper extends EntityMapper<AreaContabilEmpresaDTO, AreaContabilEmpresa> {
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    AreaContabilEmpresaDTO toDto(AreaContabilEmpresa s);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);
}
