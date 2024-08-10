package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.service.dto.AreaContabilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaContabil} and its DTO {@link AreaContabilDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaContabilMapper extends EntityMapper<AreaContabilDTO, AreaContabil> {}
