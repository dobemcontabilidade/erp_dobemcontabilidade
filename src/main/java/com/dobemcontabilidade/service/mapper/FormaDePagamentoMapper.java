package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.service.dto.FormaDePagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormaDePagamento} and its DTO {@link FormaDePagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FormaDePagamentoMapper extends EntityMapper<FormaDePagamentoDTO, FormaDePagamento> {}
