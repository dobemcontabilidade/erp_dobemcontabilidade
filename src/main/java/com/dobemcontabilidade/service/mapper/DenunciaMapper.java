package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Denuncia;
import com.dobemcontabilidade.service.dto.DenunciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Denuncia} and its DTO {@link DenunciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DenunciaMapper extends EntityMapper<DenunciaDTO, Denuncia> {}
