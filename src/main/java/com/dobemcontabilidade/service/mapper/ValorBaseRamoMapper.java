package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.ValorBaseRamo;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.dto.RamoDTO;
import com.dobemcontabilidade.service.dto.ValorBaseRamoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ValorBaseRamo} and its DTO {@link ValorBaseRamoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ValorBaseRamoMapper extends EntityMapper<ValorBaseRamoDTO, ValorBaseRamo> {
    @Mapping(target = "ramo", source = "ramo", qualifiedByName = "ramoNome")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    ValorBaseRamoDTO toDto(ValorBaseRamo s);

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
