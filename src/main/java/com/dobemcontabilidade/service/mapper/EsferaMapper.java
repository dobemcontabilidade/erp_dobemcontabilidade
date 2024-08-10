package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.service.dto.EsferaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Esfera} and its DTO {@link EsferaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EsferaMapper extends EntityMapper<EsferaDTO, Esfera> {}
