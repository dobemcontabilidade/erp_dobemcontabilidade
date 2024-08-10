package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.service.dto.DescontoPlanoContabilDTO;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DescontoPlanoContabil} and its DTO {@link DescontoPlanoContabilDTO}.
 */
@Mapper(componentModel = "spring")
public interface DescontoPlanoContabilMapper extends EntityMapper<DescontoPlanoContabilDTO, DescontoPlanoContabil> {
    @Mapping(target = "periodoPagamento", source = "periodoPagamento", qualifiedByName = "periodoPagamentoPeriodo")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    DescontoPlanoContabilDTO toDto(DescontoPlanoContabil s);

    @Named("periodoPagamentoPeriodo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "periodo", source = "periodo")
    PeriodoPagamentoDTO toDtoPeriodoPagamentoPeriodo(PeriodoPagamento periodoPagamento);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);
}
