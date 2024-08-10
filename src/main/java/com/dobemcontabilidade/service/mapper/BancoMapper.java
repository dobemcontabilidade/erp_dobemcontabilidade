package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.service.dto.BancoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banco} and its DTO {@link BancoDTO}.
 */
@Mapper(componentModel = "spring")
public interface BancoMapper extends EntityMapper<BancoDTO, Banco> {}
