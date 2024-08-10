package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PrazoAssinatura;
import com.dobemcontabilidade.service.dto.PrazoAssinaturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrazoAssinatura} and its DTO {@link PrazoAssinaturaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrazoAssinaturaMapper extends EntityMapper<PrazoAssinaturaDTO, PrazoAssinatura> {}
