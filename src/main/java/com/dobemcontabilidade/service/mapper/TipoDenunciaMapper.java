package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.TipoDenuncia;
import com.dobemcontabilidade.service.dto.TipoDenunciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDenuncia} and its DTO {@link TipoDenunciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDenunciaMapper extends EntityMapper<TipoDenunciaDTO, TipoDenuncia> {}
