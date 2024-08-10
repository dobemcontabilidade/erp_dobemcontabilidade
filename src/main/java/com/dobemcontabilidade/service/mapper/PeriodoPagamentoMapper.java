package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PeriodoPagamento} and its DTO {@link PeriodoPagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeriodoPagamentoMapper extends EntityMapper<PeriodoPagamentoDTO, PeriodoPagamento> {}
