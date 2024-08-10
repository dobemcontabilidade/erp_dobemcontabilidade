package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Cnae;
import com.dobemcontabilidade.service.dto.CnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cnae} and its DTO {@link CnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CnaeMapper extends EntityMapper<CnaeDTO, Cnae> {}
