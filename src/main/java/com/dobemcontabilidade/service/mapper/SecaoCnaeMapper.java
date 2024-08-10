package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.service.dto.SecaoCnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecaoCnae} and its DTO {@link SecaoCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SecaoCnaeMapper extends EntityMapper<SecaoCnaeDTO, SecaoCnae> {}
