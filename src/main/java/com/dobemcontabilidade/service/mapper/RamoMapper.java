package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.service.dto.RamoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ramo} and its DTO {@link RamoDTO}.
 */
@Mapper(componentModel = "spring")
public interface RamoMapper extends EntityMapper<RamoDTO, Ramo> {}
