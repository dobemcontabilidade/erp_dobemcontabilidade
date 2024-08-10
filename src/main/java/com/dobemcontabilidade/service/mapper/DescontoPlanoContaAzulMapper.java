package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.service.dto.DescontoPlanoContaAzulDTO;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.dto.PlanoContaAzulDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DescontoPlanoContaAzul} and its DTO {@link DescontoPlanoContaAzulDTO}.
 */
@Mapper(componentModel = "spring")
public interface DescontoPlanoContaAzulMapper extends EntityMapper<DescontoPlanoContaAzulDTO, DescontoPlanoContaAzul> {
    @Mapping(target = "planoContaAzul", source = "planoContaAzul", qualifiedByName = "planoContaAzulNome")
    @Mapping(target = "periodoPagamento", source = "periodoPagamento", qualifiedByName = "periodoPagamentoPeriodo")
    DescontoPlanoContaAzulDTO toDto(DescontoPlanoContaAzul s);

    @Named("planoContaAzulNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContaAzulDTO toDtoPlanoContaAzulNome(PlanoContaAzul planoContaAzul);

    @Named("periodoPagamentoPeriodo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "periodo", source = "periodo")
    PeriodoPagamentoDTO toDtoPeriodoPagamentoPeriodo(PeriodoPagamento periodoPagamento);
}
