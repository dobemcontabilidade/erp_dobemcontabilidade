package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoContabil} and its DTO {@link PlanoContabilDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanoContabilMapper extends EntityMapper<PlanoContabilDTO, PlanoContabil> {}
