package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.service.dto.AdicionalRamoDTO;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.dto.RamoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdicionalRamo} and its DTO {@link AdicionalRamoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdicionalRamoMapper extends EntityMapper<AdicionalRamoDTO, AdicionalRamo> {
    @Mapping(target = "ramo", source = "ramo", qualifiedByName = "ramoNome")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    AdicionalRamoDTO toDto(AdicionalRamo s);

    @Named("ramoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    RamoDTO toDtoRamoNome(Ramo ramo);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);
}
